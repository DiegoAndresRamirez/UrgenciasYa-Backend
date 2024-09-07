package com.urgenciasYa.service.Impl;

import com.urgenciasYa.model.UserEntity;
import com.urgenciasYa.repository.UserRepository;
import com.urgenciasYa.service.IModel.IUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserModel {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserEntity create(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
