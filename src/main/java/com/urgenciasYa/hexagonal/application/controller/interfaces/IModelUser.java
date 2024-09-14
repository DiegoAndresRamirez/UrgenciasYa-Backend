package com.urgenciasYa.hexagonal.application.controller.interfaces;

import com.urgenciasYa.hexagonal.application.controller.generic.Create;
import com.urgenciasYa.hexagonal.application.dto.request.UserRegisterDTO;


public interface IModelUser extends Create<UserRegisterDTO> {
//    ResponseEntity<String> create(UserRegisterDTO userRegisterDTO);
}
