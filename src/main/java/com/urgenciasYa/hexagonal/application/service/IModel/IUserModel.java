package com.urgenciasYa.hexagonal.application.service.IModel;

import com.urgenciasYa.hexagonal.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.hexagonal.domain.model.UserEntity;
import com.urgenciasYa.hexagonal.application.service.crud.CreateDTO;

public interface IUserModel extends CreateDTO<UserRegisterDTO,UserEntity> {
//    UserEntity create(UserRegisterDTO userRegisterDTO);
}
