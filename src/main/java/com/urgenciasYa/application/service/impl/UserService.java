package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.*;
import com.urgenciasYa.application.dto.response.*;
import com.urgenciasYa.application.service.IModel.IUserModel;
import com.urgenciasYa.domain.model.Eps;
import com.urgenciasYa.domain.model.RoleEntity;
import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.persistence.EpsRepository;
import com.urgenciasYa.infrastructure.persistence.RoleRepository;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserModel {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EpsRepository epsRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserEntity create(UserRegisterRequestDTO userRegisterDTO) {
        UserEntity existUser = userRepository.findByEmail(userRegisterDTO.getEmail());
        if (existUser != null) {
            throw new IllegalArgumentException("El correo ya existe");
        }

        // Aquí deberías obtener el EPS del DTO directamente
        String epsName = userRegisterDTO.getEps().getName();
        // Si necesitas buscar el EPS en la base de datos, puedes hacerlo aquí
        Eps epsExists = epsRepository.findByName(epsName);
        if (epsExists == null) {
            throw new IllegalArgumentException("La EPS no existe");
        }

        RoleEntity defaultRole = roleRepository.findRoleByCode("USER")
                .orElseThrow(() -> new RuntimeException("Rol 'USER' no encontrado."));

        UserEntity user = UserEntity.builder()
                .name(userRegisterDTO.getName())
                .email(userRegisterDTO.getEmail())
                .password(encoder.encode(userRegisterDTO.getPassword()))
                .eps(epsExists.getName()) // Usar el nombre de EPS encontrado
                .role(defaultRole)
                .document(userRegisterDTO.getDocument())
                .build();

        return userRepository.save(user);
    }



    public LoginDTO verify(UserEntity user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginDTO loginDTO = LoginDTO.builder()
                    .id(userRepository.findByName(user.getName()).getId())
                    .token(jwtService.generateToken(user.getName()))
                    .build();
            return loginDTO;
        } else {
            return LoginDTO.builder().build();
        }
    }

    @Override
    public List<UserResponseDTO> readAll() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(userEntity -> UserResponseDTO.builder()
                        .id(userEntity.getId())
                        .name(userEntity.getName())
                        .eps(userEntity.getEps())
                        .email(userEntity.getEmail())
                        .document(userEntity.getDocument())
                        .emergency(userEntity.getEmergency() != null ? EmergencyContactRequestDTO.builder()
                                .name(userEntity.getEmergency().getName())
                                .phone(userEntity.getEmergency().getPhone())
                                .build() : null)
                        .role(userEntity.getRole() != null ? RoleResponseDTO.builder()
                                .code(userEntity.getRole().getCode())
                                .build() : null)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado");
        }

        userRepository.deleteById(id);
    }

    @Override
    public UserRegisterDTO getById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();

            Eps epsExists = epsRepository.findByName(user.getEps());

            EpsUserResponseDTO epsUserResponseDTO = EpsUserResponseDTO.builder()
                    .id(epsExists.getId())
                    .name(epsExists.getName())
                    .build();

            EmergencyContactResponseDTO emergencyContactResponseDTO = EmergencyContactResponseDTO.builder()
                    .id(user.getEmergency().getId())
                    .name(user.getEmergency().getName())
                    .phone(user.getEmergency().getPhone())
                    .build();

            return UserRegisterDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .eps(epsUserResponseDTO)
                    .password(user.getPassword())
                    .contact(emergencyContactResponseDTO)
                    .document(user.getDocument())
                    .build();
        } else {
            throw new IllegalArgumentException("Usuario con ID " + id + " no encontrado");
        }
    }

    @Override
    public UserEntity update(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + id + " no encontrado"));

        // Usar el EPS del DTO
        String epsName = userUpdateDTO.getEps().getName();
        Eps epsExists = epsRepository.findByName(epsName);
        if (epsExists == null) {
            throw new IllegalArgumentException("La EPS no existe");
        }

        existingUser.setName(userUpdateDTO.getName());
        existingUser.setEmail(userUpdateDTO.getEmail());
        existingUser.setEps(epsExists.getName());
        existingUser.setDocument(userUpdateDTO.getDocument());

        return userRepository.save(existingUser);
    }

    public void changePassword(Long id, ChangePasswordDTO passwordChangeDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + id + " no encontrado"));

        // Verificar la contraseña actual
        if (!encoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        // Verificar que la nueva contraseña y su confirmación coincidan
        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmNewPassword())) {
            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden");
        }

        // Establecer la nueva contraseña
        user.setPassword(encoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);
    }


}

