package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.*;
import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.domain.model.Hospital;

/*
 * Interface representing the model for Hospital entities.
 * This interface extends several generic interfaces for CRUD operations on Hospital entities.
 */

public interface IModelHospital extends Create<HospitalCreateResponseDTO>, // Interface for creating Hospital entities
        Update<HospitalCreateResponseDTO, Long>, // Interface for updating Hospital entities by ID
        Delete<Long>, // Interface for deleting Hospital entities by ID
        GetById<Hospital, Long>, // Interface for retrieving a Hospital entity by ID
        GetAll<Hospital> { // Interface for retrieving all Hospital entities
}
