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
@CrossOrigin(origins = "http://localhost:3000")
public class EmergencyContactController implements IModelEmergencyContact {

    @Autowired
    private EmergencyContactService emergencyContactService;

    @Operation(
            summary = "Crear un nuevo contacto de emergencia",
            description = "Este endpoint permite crear un nuevo contacto de emergencia para un usuario específico. " +
                    "Se requiere que el usuario exista y que no tenga ya un contacto de emergencia registrado.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Contacto de emergencia creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = EmergencyEntity.class,
                                            example = "{\"message\": \"Contacto de emergencia creado con éxito\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de entrada inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "string",
                                            example = "Los campos 'name' y 'phone' son obligatorios."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "string",
                                            example = "No se encontró el usuario con ID {userId}."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "El usuario ya tiene un contacto de emergencia",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "string",
                                            example = "El usuario ya tiene un contacto de emergencia registrado."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "string",
                                            example = "Error interno del servidor. Por favor, inténtelo de nuevo más tarde."
                                    )
                            )
                    )
            }
    )
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
            summary = "Actualizar un contacto de emergencia existente",
            description = "Actualiza un contacto de emergencia identificado por el ID proporcionado. " +
                    "Devuelve el mensaje de éxito o un error si la operación falla.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacto de emergencia actualizado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = EmergencyEntity.class,
                                            example = "{\"message\": \"Contacto de emergencia actualizado con éxito\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida, si los datos de entrada son incorrectos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorSimple.class,
                                            example = "{\"code\": 400, \"status\": \"BAD_REQUEST\", \"message\": \"El nombre y el teléfono son obligatorios.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No encontrado, si el contacto con el ID dado no existe",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorSimple.class,
                                            example = "{\"code\": 404, \"status\": \"NOT_FOUND\", \"message\": \"No se encontró el contacto con ID {id}.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor, si algo sale mal",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorSimple.class,
                                            example = "{\"code\": 500, \"status\": \"INTERNAL_SERVER_ERROR\", \"message\": \"Error interno del servidor.\"}"
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String phone) {
        try {
            this.emergencyContactService.update(id, name, phone);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Contacto de emergencia actualizado con éxito");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorSimple);
        } catch (EntityNotFoundException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("No se encontró el contacto con ID " + id)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error interno del servidor. Por favor, inténtelo de nuevo más tarde.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

}
