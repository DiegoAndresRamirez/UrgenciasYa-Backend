package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.request.UserRegisterRequestDTO;
import com.urgenciasYa.application.dto.request.UserUpdateDTO;
import com.urgenciasYa.application.dto.response.UserResponseDTO;
import com.urgenciasYa.application.service.crud.*;
import com.urgenciasYa.domain.model.UserEntity;
import java.util.List;

/*
 * Interface for operations related to User management.
 * This interface extends the CRUD operations for user management.
 */

public interface IUserModel extends CreateDTO<UserRegisterRequestDTO,UserEntity> , Delete<Long>, ReadById<UserRegisterDTO, Long> {
    List<UserResponseDTO> readAll();

    // Method to update an existing user.
    UserEntity update(Long id, UserUpdateDTO userRegisterDTO);
}
