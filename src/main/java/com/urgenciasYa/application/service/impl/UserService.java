package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.service.IModel.IUserModel;
import com.urgenciasYa.domain.model.RoleEntity;
import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.persistence.RoleRepository;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public String verify(UserEntity user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getName());
        } else {
            return "fail";
        }
    }

}

