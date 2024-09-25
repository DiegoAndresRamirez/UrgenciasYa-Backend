package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.request.UserRegisterRequestDTO;
import com.urgenciasYa.application.dto.request.UserUpdateDTO;
import com.urgenciasYa.application.dto.response.EpsResponseDTO;
import com.urgenciasYa.application.dto.response.EpsUserResponseDTO;
import com.urgenciasYa.application.dto.response.LoginDTO;
import com.urgenciasYa.application.dto.response.UserResponseDTO;
import com.urgenciasYa.domain.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userRegisterRequestDTOtoUserEntity(UserRegisterRequestDTO userRegisterRequestDTO);

    LoginDTO userEntityToLoginDTO(UserEntity userEntity);

    UserResponseDTO userEntityToUserResponseDTO(UserEntity userEntity);

//    UserRegisterDTO userEntityToUserRegisterDTO(UserEntity userEntity);

    UserEntity userUpdateDTOToUserEntity(UserUpdateDTO userUpdateDTO);

    String epsResponseDTOToUserEntity(EpsResponseDTO epsResponseDTO);

}
