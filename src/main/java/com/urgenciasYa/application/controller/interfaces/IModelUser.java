package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.GetAll;
import com.urgenciasYa.application.dto.request.UserRegisterDTO;


public interface IModelUser extends Create<UserRegisterDTO>, GetAll<UserRegisterDTO> {
}
