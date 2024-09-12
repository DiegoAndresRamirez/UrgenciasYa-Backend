package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.controller.generic.Create;
import com.urgenciasYa.dto.request.UserRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface IModelUser extends Create<String,UserRegisterDTO> {
    ResponseEntity<String> create(UserRegisterDTO userRegisterDTO);
}
