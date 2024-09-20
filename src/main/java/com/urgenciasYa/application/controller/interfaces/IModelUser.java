package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.GetAll;
import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.request.UserRegisterRequestDTO;


public interface IModelUser extends Create<UserRegisterRequestDTO>, GetAll<UserRegisterDTO> , Delete<Long> {
}
