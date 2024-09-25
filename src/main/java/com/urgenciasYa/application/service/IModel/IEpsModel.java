package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.application.service.crud.*;
import com.urgenciasYa.domain.model.Eps;

/*
 * Interface for EPS model operations.
 * This interface defines the contract for CRUD operations related to EPS entities.
 */

public interface IEpsModel extends ReadAll<Eps>, // Read all EPS entities
        CreateDTO<EpsRequestDTO, Eps>, // Create a new EPS entity
        Update<Integer, Eps>, // Update an existing EPS entity by its ID
        Delete<Integer> , // Delete an EPS entity by its ID
        ReadById<Eps ,Integer>{ // Read an EPS entity by its ID


}
