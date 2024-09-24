package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.request.UserRegisterRequestDTO;
import com.urgenciasYa.domain.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userRegisterRequestDTOtoUserEntity(UserRegisterRequestDTO userRegisterRequestDTO);

}
