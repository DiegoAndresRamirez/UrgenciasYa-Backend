package com.urgenciasYa.service.IModel;

import com.urgenciasYa.dto.request.UserRegisterDTO;
import com.urgenciasYa.model.UserEntity;
import com.urgenciasYa.service.crud.CreateDTO;

public interface IUserModel extends CreateDTO<UserRegisterDTO,UserEntity> {
//    UserEntity create(UserRegisterDTO userRegisterDTO);
}
