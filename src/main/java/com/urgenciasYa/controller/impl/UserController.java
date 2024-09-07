package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelUser;
import com.urgenciasYa.dto.request.UserRegisterDTO;
import com.urgenciasYa.service.IModel.IUserModel;
import com.urgenciasYa.service.Impl.UserService;
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
    UserService userService;


    @Override
    @PostMapping("/register")
    public ResponseEntity<String> create(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userService.create(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
    }
}
