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
import org.apache.catalina.User;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    @Autowired
    UserMapper userMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Method to create a new user
    @Override
    public UserEntity create(UserRegisterRequestDTO userRegisterDTO) {
        UserEntity existUser = userRepository.findByEmail(userRegisterDTO.getEmail());
        if (existUser != null) {
            throw new IllegalArgumentException("El correo ya existe");
        }

        // Get EPS name from the registration DTO
        String epsName = userRegisterDTO.getEps().getName();
        // Check if the EPS exists in the database
        Eps epsExists = epsRepository.findByName(epsName);
        if (epsExists == null) {
            throw new IllegalArgumentException("La EPS no existe");
        }

        RoleEntity defaultRole = roleRepository.findRoleByCode("USER")
                .orElseThrow(() -> new RuntimeException("Rol 'USER' no encontrado."));

        UserEntity user = UserMapper.INSTANCE.userRegisterRequestDTOtoUserEntity(userRegisterDTO);
        user.setEps(UserMapper.INSTANCE.epsResponseDTOToUserEntity(userRegisterDTO.getEps())); // Usar el nombre de EPS encontrado
        user.setRole(defaultRole);
        user.setPassword(encoder.encode(userRegisterDTO.getPassword()));


        return userRepository.save(user);
    }



    public LoginDTO verify(UserEntity user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginDTO loginDTO = LoginDTO.builder()
                    .id(userRepository.findByName(user.getName()).getId())
                    .role_id(userRepository.findByName(user.getName()).getRole().getId())
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
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();

        users.forEach(e -> {
                    UserResponseDTO userResponse = userMapper.userEntityToUserResponseDTO(e);
                    userResponse.setEmergency(e.getEmergency() != null ? EmergencyContactRequestDTO.builder()
                                .name(e.getEmergency().getName())
                                .phone(e.getEmergency().getPhone())
                                .build() : null);
                    userResponse.setRole(e.getRole() != null ? RoleResponseDTO.builder()
                            .code(e.getRole().getCode())
                            .build() : null);
                    userResponseDTOS.add(userResponse);
        }

                );

        return userResponseDTOS;
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

            EmergencyContactResponseDTO emergencyContactResponseDTO = null; // Initialize emergency contact as null

            if (user.getEmergency() != null) { // Check if emergency contact exists and build its DTO
                emergencyContactResponseDTO = EmergencyContactResponseDTO.builder()
                        .id(user.getEmergency().getId())
                        .name(user.getEmergency().getName())
                        .phone(user.getEmergency().getPhone())
                        .build();
            }

            return UserRegisterDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .eps(epsUserResponseDTO) // Set EPS DTO
                    .password(user.getPassword()) // Include password (consider security implications)
                    .contact(emergencyContactResponseDTO) // Set emergency contact (can be null)
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

        // Get EPS name from the update DTO
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

        // Verify the current password
        if (!encoder.matches(passwordChangeDTO.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        // Verify that the new password matches the confirmation
        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmNewPassword())) {
            throw new IllegalArgumentException("La nueva contraseña y su confirmación no coinciden");
        }

        // Set the new password and save the user
        user.setPassword(encoder.encode(passwordChangeDTO.getNewPassword()));
        userRepository.save(user);
    }


}

