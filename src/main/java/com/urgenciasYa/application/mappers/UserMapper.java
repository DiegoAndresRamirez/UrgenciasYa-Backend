package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.request.UserRegisterRequestDTO;
import com.urgenciasYa.application.dto.response.LoginDTO;
import com.urgenciasYa.application.dto.response.UserResponseDTO;
import com.urgenciasYa.domain.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userRegisterRequestDTOtoUserEntity(UserRegisterRequestDTO userRegisterRequestDTO);

    LoginDTO userEntityToLoginDTO(UserEntity userEntity);

    UserResponseDTO userEntityToUserResponseDTO(UserEntity userEntity);

}
