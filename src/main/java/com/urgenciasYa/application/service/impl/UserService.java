package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.*;
import com.urgenciasYa.application.dto.response.*;
import com.urgenciasYa.application.mappers.UserMapper;
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

        UserEntity user = UserMapper.INSTANCE.userRegisterRequestDTOtoUserEntity(userRegisterDTO);
        user.setEps(epsExists.getName()); // Usar el nombre de EPS encontrado
        user.setRole(defaultRole);
        user.setPassword(encoder.encode(userRegisterDTO.getPassword()));

        return userRepository.save(user);
    }



    public LoginDTO verify(UserEntity user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginDTO loginDTO = UserMapper.INSTANCE.userEntityToLoginDTO(user);
            loginDTO.setToken(jwtService.generateToken(user.getName()));
            return loginDTO;
        } else {
            return LoginDTO.builder().build();
        }
    }

    @Override
    public List<UserResponseDTO> readAll() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper.INSTANCE::userEntityToUserResponseDTO)
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

            // Usar el mapeador para convertir la entidad UserEntity a UserRegisterDTO
            UserRegisterDTO userRegisterDTO = UserMapper.INSTANCE.userEntityToUserRegisterDTO(user);

            // Establecer los campos adicionales
            Eps epsExists = epsRepository.findByName(user.getEps());
            userRegisterDTO.setEps(EpsUserResponseDTO.builder()
                    .id(epsExists.getId())
                    .name(epsExists.getName())
                    .build());

            if (user.getEmergency() != null) {
                userRegisterDTO.setContact(EmergencyContactResponseDTO.builder()
                        .id(user.getEmergency().getId())
                        .name(user.getEmergency().getName())
                        .phone(user.getEmergency().getPhone())
                        .build());
            }

            return userRegisterDTO;
        } else {
            throw new IllegalArgumentException("Usuario con ID " + id + " no encontrado");
        }
    }


    @Override
    public UserEntity update(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID " + id + " no encontrado"));

        // Usar el mapeador para convertir el UserUpdateDTO a la entidad UserEntity
        UserEntity updatedUser = UserMapper.INSTANCE.userUpdateDTOToUserEntity(userUpdateDTO);

        // Establecer los campos adicionales
        String epsName = updatedUser.getEps();
        Eps epsExists = epsRepository.findByName(epsName);
        if (epsExists == null) {
            throw new IllegalArgumentException("La EPS no existe");
        }
        updatedUser.setEps(epsExists.getName());

        // Actualizar los campos en la entidad existente
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setEps(updatedUser.getEps());
        existingUser.setDocument(updatedUser.getDocument());

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

