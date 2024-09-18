package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.controller.interfaces.IModelEmergencyContact;
import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.exceptions.ErrorSimple;
import com.urgenciasYa.application.service.impl.EmergencyContactService;
import com.urgenciasYa.application.service.impl.UserService;
import com.urgenciasYa.domain.model.EmergencyEntity;
import com.urgenciasYa.domain.model.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/contacts")
@Tag(name = "Emergency Contacts", description = "Endpoints for managing emergency contacts")
public class EmergencyContactController implements IModelEmergencyContact {

    @Autowired
    private EmergencyContactService emergencyContactService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Crear un nuevo contacto de emergencia",
            description = "Crea un nuevo contacto de emergencia para un usuario específico")
    @ApiResponse(responseCode = "201", description = "Contacto de emergencia creado exitosamente",
            content = @Content(schema = @Schema(implementation = EmergencyEntity.class)))
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @ApiResponse(responseCode = "409", description = "El usuario ya tiene un contacto de emergencia")
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> create(
            @PathVariable Long userId,
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String phone) {
        try {
            emergencyContactService.create(userId, name, phone);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Contacto de emergencia creado con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
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


    @Override
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an existing emergency contact",
            description = "Deletes an existing emergency contact identified by the given ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Emergency contact deleted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = "string", example = "Emergency contact deleted successfully"))),
                    @ApiResponse(responseCode = "404", description = "Not Found, if the contact with the given ID does not exist",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorSimple.class)))
            }
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            emergencyContactService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Emergency contact deleted successfully");
        } catch (Exception ex) {
            ErrorSimple errorSimple;
            if (ex instanceof IllegalArgumentException) {
                errorSimple = ErrorSimple.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND.name())
                        .message("Emergency contact with the given ID not found")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
            } else {
                errorSimple = ErrorSimple.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message("An unexpected error occurred")
                        .build();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
            }
        }
    }
}
