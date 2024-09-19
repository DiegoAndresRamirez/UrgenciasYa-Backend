package com.urgenciasYa.application.controller.impl;

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

@RestController
@RequestMapping("/api/shifts")
@CrossOrigin(origins = "http://localhost:3000")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

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
            Shift shift = shiftService.createShift(document, hospitalId, epsId);
            return ResponseEntity.status(HttpStatus.CREATED).body(shift);
        } catch (IllegalArgumentException e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (IllegalStateException e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST.name())
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimple);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Internal server error: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

}
