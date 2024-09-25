package com.urgenciasYa.application.controller.interfaces;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;

public interface IModelEmergencyContact {
    /*
     * Method to update an emergency contact's information.
     */
    ResponseEntity<?> update(Long id, @NotBlank String name, @NotBlank String phone);
}

