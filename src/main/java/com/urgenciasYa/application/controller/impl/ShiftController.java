package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.dto.response.*;
import com.urgenciasYa.application.exceptions.ErrorSimple;
import com.urgenciasYa.domain.model.Shift;
import com.urgenciasYa.application.service.impl.ShiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
 * This class serves as the controller for managing emergency shift operations.
 * It provides endpoints for creating and retrieving shifts based on user information.
 */

@RestController
@RequestMapping("/api/v1/shifts")
@CrossOrigin(origins = "https://urgenciasya-frontend-3.onrender.com")
public class ShiftController {

    @Autowired
    private ShiftService shiftService; // Inject the ShiftService for business logic

    /*
     * Endpoint to create an emergency shift for a user at the hospital assigned by their EPS.
     */

    @PostMapping("/create")
    @Operation(
            summary = "Create an emergency shift",
            description = "Allows users to request an emergency shift at the hospital assigned by their EPS. Returns the details of the created shift.",
            tags = {"Shift"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Shift created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Shift.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "404", description = "User, hospital, or EPS not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> createShift(
            @Parameter(description = "User's document number") @RequestParam String document,
            @Parameter(description = "Hospital ID") @RequestParam Long hospitalId,
            @Parameter(description = "EPS ID") @RequestParam Integer epsId
    ) {
        try {
            // Call the service layer to create a new emergency shift
            Shift shift = shiftService.createShift(document, hospitalId, epsId);

            // Build the user information for the response
            UserResponseDTO userDTO = UserResponseDTO.builder()
                    .id(shift.getUser().getId())
                    .name(shift.getUser().getName())
                    .eps(shift.getUser().getEps())
                    .email(shift.getUser().getEmail())
                    .document(shift.getUser().getDocument())
                    .emergency(shift.getUser().getEmergency() != null ? EmergencyContactRequestDTO.builder()
                            .name(shift.getUser().getEmergency().getName())
                            .phone(shift.getUser().getEmergency().getPhone())
                            .build() : null)
                    .role(shift.getUser().getRole() != null ? RoleResponseDTO.builder()
                            .code(shift.getUser().getRole().getCode())
                            .build() : null)
                    .build();

            // Build the shift information for the response
            UserShiftResponseDTO shiftDTO = UserShiftResponseDTO.builder()
                    .id(shift.getId())
                    .shiftNumber(shift.getShiftNumber())
                    .estimatedTime(shift.getEstimatedTime())
                    .status(shift.getStatus().name())
                    .user(userDTO)
                    .hospitalId(HospitalShiftResponseDTO.builder()
                            .id(shift.getHospital().getId())
                            .name(shift.getHospital().getName())
                            .build())
                    .epsId(EpsShiftResponseDTO.builder()
                            .id(shift.getEps().getId())
                            .name(shift.getEps().getName())
                            .build()) // Solo ID de la EPS
                    .build();

            // Return the created shift details in a 201 Created response
            return ResponseEntity.status(HttpStatus.CREATED).body(shiftDTO);
        } catch (IllegalArgumentException e) {
            // Handle case where user, hospital, or EPS is not found, and return a 404 error response
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (IllegalStateException e) {
            // Handle case of invalid request parameters, and return a 400 Bad Request response
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimple);
        } catch (Exception e) {
            // Handle unexpected errors and return a 500 Internal Server Error response
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Internal server error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

    /*
     * Endpoint to retrieve all shifts associated with a specific user based on their document number.
     */

    @GetMapping("/user/{document}")
    @Operation(
            summary = "Get all shifts for a specific user",
            description = "Retrieve all shifts associated with a specific user identified by their document number.",
            tags = {"Shift"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shifts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User or shifts not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> getAllShiftsByUser(@PathVariable String document) {
        try {
            // Fetch all shifts for the user using their document number
            List<UserShiftResponseDTO> shiftsDTO = shiftService.getAllShiftsByUser(document);
            return ResponseEntity.ok(shiftsDTO);
        } catch (IllegalArgumentException e) {
            // Handle case where user or shifts are not found, and return a 404 error response
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception e) {
            // Handle unexpected errors and return a 500 Internal Server Error response
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Internal server error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

}
