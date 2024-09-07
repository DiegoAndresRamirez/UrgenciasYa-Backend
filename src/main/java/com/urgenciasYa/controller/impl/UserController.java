package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelUser;
import com.urgenciasYa.dto.request.UserRegisterDTO;
import com.urgenciasYa.model.UserEntity;
import com.urgenciasYa.service.IModel.IUserModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController implements IModelUser {

    @Autowired
    IUserModel userService;


    @Override
    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> create(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userRegisterDTO).getBody());
    }
}
