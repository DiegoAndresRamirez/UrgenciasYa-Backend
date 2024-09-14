package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.controller.generic.Create;
import com.urgenciasYa.hexagonal.application.dto.request.UserRegisterDTO;


public interface IModelUser extends Create<UserRegisterDTO> {
//    ResponseEntity<String> create(UserRegisterDTO userRegisterDTO);
}
