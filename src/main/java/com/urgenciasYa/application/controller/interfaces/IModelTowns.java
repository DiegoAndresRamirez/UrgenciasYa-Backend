package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.Update;
import com.urgenciasYa.application.dto.request.TownCreateDTO;
import com.urgenciasYa.domain.model.Towns;
import org.springframework.http.ResponseEntity;

/*
 * Interface representing the model for Town entities.
 * This interface extends several generic interfaces for CRUD operations on Town entities.
 */

public interface IModelTowns extends Create<TownCreateDTO>, // Interface for creating Town entities
        Delete<Integer> ,  // Interface for deleting Town entities by ID
        Update<Integer, Towns>{ // Interface for updating Town entities by ID
    public ResponseEntity<?> getAllTowns();} // Method declaration for retrieving all towns

