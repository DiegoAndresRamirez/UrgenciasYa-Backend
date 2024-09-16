package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.response.LoginDTO;
import com.urgenciasYa.application.dto.response.RoleResponseDTO;
import com.urgenciasYa.application.dto.response.UserResponseDTO;
import com.urgenciasYa.application.service.IModel.IUserModel;
import com.urgenciasYa.domain.model.RoleEntity;
import com.urgenciasYa.domain.model.UserEntity;
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

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserEntity create(UserRegisterDTO userRegisterDTO) {
        UserEntity existUser= userRepository.findByEmail(userRegisterDTO.getEmail());
        if(existUser != null){
            throw new IllegalArgumentException("El correo ya existe");
        }

        RoleEntity defaultRole = roleRepository.findRoleByCode("USER")
                .orElseThrow(() -> new RuntimeException("Rol 'USER' no encontrado."));

        UserEntity user = UserEntity.builder()
                .name(userRegisterDTO.getName())
                .email(userRegisterDTO.getEmail())
                .password(encoder.encode(userRegisterDTO.getPassword()))
                .eps(userRegisterDTO.getEps())
                .role(defaultRole)
                .document(userRegisterDTO.getDocument())
                .build();

        return userRepository.save(user);
    }
    public LoginDTO verify(UserEntity user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            LoginDTO loginDTO = LoginDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .document(user.getDocument())
                    .eps(user.getEps())
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
}

