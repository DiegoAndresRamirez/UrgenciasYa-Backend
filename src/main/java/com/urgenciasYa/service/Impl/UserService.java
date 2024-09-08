package com.urgenciasYa.service.Impl;

import com.urgenciasYa.dto.request.UserRegisterDTO;
import com.urgenciasYa.model.RoleEntity;
import com.urgenciasYa.model.UserEntity;
import com.urgenciasYa.repository.UserRepository;
import com.urgenciasYa.service.IModel.IUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserModel {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;




    @Override
    public UserEntity create(UserRegisterDTO userRegisterDTO) {
        UserEntity existUser= userRepository.findByEmail(userRegisterDTO.getEmail());
        if(existUser != null){
            throw new IllegalArgumentException("El correo ya existe");
        }
        UserEntity user = UserEntity.builder()
                .name(userRegisterDTO.getName())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .eps(userRegisterDTO.getEps())
                .role(defaultRole)
                .build();

        return userRepository.save(user);
    }

}

