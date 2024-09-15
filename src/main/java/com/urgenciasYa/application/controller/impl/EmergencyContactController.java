package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.controller.interfaces.IModelEmergencyContact;
import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.service.impl.EmergencyContactService;
import com.urgenciasYa.domain.model.EmergencyEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contacts")
@Tag(name = "Emergency Contacts", description = "Endpoints for managing emergency contacts")
public class EmergencyContactController implements IModelEmergencyContact {

    @Autowired
    private EmergencyContactService emergencyContactService;

    @Override
    @PostMapping("/create")
    @Operation(
            summary = "Create a new emergency contact",
            description = "Creates a new emergency contact and returns the created contact.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Emergency contact created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmergencyEntity.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong")
            }
    )
    public ResponseEntity<?> create(@RequestBody EmergencyContactRequestDTO dto) {
        EmergencyEntity emergencyEntity = emergencyContactService.create(dto);
        if(emergencyEntity != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(emergencyEntity);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
