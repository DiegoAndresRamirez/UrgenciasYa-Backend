package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.service.IModel.IUserModel;
import com.urgenciasYa.domain.model.RoleEntity;
import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.persistence.RoleRepository;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserModel {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;




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
                .password(userRegisterDTO.getPassword())
                .eps(userRegisterDTO.getEps())
                .role(defaultRole)
                .document(userRegisterDTO.getDocument())
                .build();

        return userRepository.save(user);
    }

}

