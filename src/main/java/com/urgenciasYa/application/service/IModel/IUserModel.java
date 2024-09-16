package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.response.UserResponseDTO;
import com.urgenciasYa.application.service.crud.ReadAll;
import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.application.service.crud.CreateDTO;

import java.util.List;

public interface IUserModel extends CreateDTO<UserRegisterDTO,UserEntity>  {
    List<UserResponseDTO> readAll();
}
