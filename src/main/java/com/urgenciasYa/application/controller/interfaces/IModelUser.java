package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.GetAll;
import com.urgenciasYa.application.dto.request.UserRegisterDTO;
import com.urgenciasYa.application.dto.request.UserRegisterRequestDTO;

/*
 * Interface representing the model for User entities.
 * This interface extends several generic interfaces for CRUD operations on User entities.
 */

public interface IModelUser extends Create<UserRegisterRequestDTO>, // Interface for creating User entities using UserRegisterRequestDTO
        GetAll<UserRegisterDTO> , // Interface for retrieving all User entities in UserRegisterDTO format
        Delete<Long> { // Interface for deleting User entities by their ID (Long type)
}
