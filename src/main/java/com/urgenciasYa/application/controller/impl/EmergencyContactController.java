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
            summary = "Create a new emergency contact",
            description = "This endpoint allows the creation of a new emergency contact for a specific user. " +
                    "The user must exist and should not already have an emergency contact registered.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Emergency contact created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = EmergencyEntity.class,
                                            example = "{\"message\": \"Emergency contact created successfully\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "string",
                                            example = "The fields 'name' and 'phone' are required."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "string",
                                            example = "User with ID {userId} not found."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User already has an emergency contact",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "string",
                                            example = "The user already has an emergency contact registered."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "string",
                                            example = "Internal server error. Please try again later."
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
           EmergencyEntity emergencyEntity = emergencyContactService.create(userId, name, phone);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Emergency contact created successfully");
            response.put("idContact", emergencyEntity.getId().toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }



    @Override
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing emergency contact",
            description = "Updates an emergency contact identified by the provided ID. " +
                    "Returns a success message or an error if the operation fails.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Emergency contact updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = EmergencyEntity.class,
                                            example = "{\"message\": \"Emergency contact updated successfully\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request, if the input data is invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorSimple.class,
                                            example = "{\"code\": 400, \"status\": \"BAD_REQUEST\", \"message\": \"Name and phone are required.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found, if the contact with the given ID does not exist",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorSimple.class,
                                            example = "{\"code\": 404, \"status\": \"NOT_FOUND\", \"message\": \"Contact with ID {id} not found.\"}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error, if something goes wrong",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorSimple.class,
                                            example = "{\"code\": 500, \"status\": \"INTERNAL_SERVER_ERROR\", \"message\": \"Internal server error.\"}"
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
            emergencyContactService.update(id, name, phone);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Emergency contact updated successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact with ID " + id + " not found");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }


}
