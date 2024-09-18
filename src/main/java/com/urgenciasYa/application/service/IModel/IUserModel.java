package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.response.UserResponseDTO;
import com.urgenciasYa.application.service.crud.*;
import com.urgenciasYa.domain.model.UserEntity;

import java.util.List;

public interface IUserModel extends CreateDTO<UserRegisterDTO,UserEntity> , Delete<Long>, ReadById<UserRegisterDTO, Long> {
    List<UserResponseDTO> readAll();

    void update(Long id, UserRegisterDTO userRegisterDTO);
}
