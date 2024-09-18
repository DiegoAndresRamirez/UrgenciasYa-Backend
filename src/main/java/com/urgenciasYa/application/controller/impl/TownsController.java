package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.controller.interfaces.IModelTowns;
import com.urgenciasYa.application.dto.request.TownCreateDTO;
import com.urgenciasYa.application.dto.response.TownsDTO;
import com.urgenciasYa.application.service.IModel.ITownsModel;
import com.urgenciasYa.infrastructure.handleError.SuccessResponse;
import com.urgenciasYa.application.exceptions.ErrorSimple;
import com.urgenciasYa.domain.model.Towns;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/town")
@CrossOrigin(origins = "http://localhost:3000")
public class TownsController implements IModelTowns {

    @Autowired
    ITownsModel townsService;

    @GetMapping
    @Operation(
            summary = "Gets a list of all available cities",
            description = "Retrieves a list of all available cities from the service."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List obtained successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TownsDTO.class))),
            @ApiResponse(responseCode = "404", description = "No cities found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> getAllTowns() {
        try {
            List<Towns> towns = townsService.readALl();
            if (towns.isEmpty()) {
                ErrorSimple errorSimple = ErrorSimple.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND.name())
                        .message("No cities found")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
            }

            List<TownsDTO> townsDTOS = towns.stream()
                    .map(town -> TownsDTO.builder()
                            .name(town.getName())
                            .build())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(townsDTOS);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get town by ID",
            description = "Retrieves a town by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Town retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TownsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found, if the town with the given ID does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> getById(
            @Parameter(description = "ID of the town to be retrieved") @PathVariable Integer id) {
        try {
            Towns town = townsService.getById(id);
            if (town == null) {
                ErrorSimple errorSimple = ErrorSimple.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND.name())
                        .message("Town not found")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
            }

            TownsDTO townDTO = TownsDTO.builder()
                    .name(town.getName())
                    .build();

            return ResponseEntity.ok(townDTO);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create a new town",
            description = "Creates a new town with the provided details and returns the success response."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Town created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, if the input data is invalid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "409", description = "Conflict, if the town already exists or there's a conflict",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> create(@RequestBody @Valid TownCreateDTO dto) {
        try {
            townsService.create(dto);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .status(HttpStatus.CREATED.name())
                    .message("Municipio creado con éxito")
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.CONFLICT.value())
                    .status(HttpStatus.CONFLICT.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error interno del servidor: " + exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing town",
            description = "Updates an existing town identified by the given ID with the provided details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Town updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, if the input data is invalid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "404", description = "Not Found, if the town with the given ID does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID of the town to be updated") @PathVariable Integer id,
            @RequestBody @Valid Towns towns) {
        try {
            townsService.update(id, towns);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("Municipio actualizado con éxito")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error interno del servidor: " + exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a town by ID",
            description = "Deletes the town with the specified ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Town deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found, if the town with the given ID does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, if something goes wrong",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
    })
    public ResponseEntity<?> delete(
            @Parameter(description = "ID of the town to be deleted") @PathVariable Integer id) {
        try {
            townsService.delete(id);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("Municipio borrado con éxito")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("Municipio con el ID proporcionado no encontrado")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("Error interno del servidor: " + exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }
}


