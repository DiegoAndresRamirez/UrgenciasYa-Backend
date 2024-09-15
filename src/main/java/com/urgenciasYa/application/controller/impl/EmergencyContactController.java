package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.controller.interfaces.IModelEmergencyContact;
import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.exceptions.ErrorSimple;
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
import org.springframework.web.bind.annotation.*;

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
                    @ApiResponse(responseCode = "400", description = "Bad Request, if the input data is invalid",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class)))
            }
    )
    public ResponseEntity<?> create(@RequestBody EmergencyContactRequestDTO dto) {
        try {
            EmergencyEntity emergencyEntity = emergencyContactService.create(dto);
            if (emergencyEntity != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(emergencyEntity);
            } else {
                ErrorSimple errorSimple = ErrorSimple.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message("Failed to create emergency contact")
                        .build();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
            }
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

    @Override
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing emergency contact",
            description = "Updates an existing emergency contact identified by the given ID and returns the updated contact.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Emergency contact updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmergencyEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request, if the input data is invalid",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found, if the contact with the given ID does not exist",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class)))
            }
    )
    public ResponseEntity<?> update(@RequestBody EmergencyContactRequestDTO emergencyContactRequestDTO, @PathVariable Long id) {
        try {
            EmergencyEntity emergency = this.emergencyContactService.Update(id, emergencyContactRequestDTO);

            if (emergency != null) {
                return ResponseEntity.status(HttpStatus.OK).body(emergency);
            } else {
                ErrorSimple errorSimple = ErrorSimple.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND.name())
                        .message("Emergency contact with the given ID not found")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
            }
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }
}
