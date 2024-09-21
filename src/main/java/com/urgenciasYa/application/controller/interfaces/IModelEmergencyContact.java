package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.Update;
import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;

public interface IModelEmergencyContact {
    ResponseEntity<?> update(Long id, @NotBlank String name, @NotBlank String phone);
}

