package com.urgenciasYa.application.controller.impl;

import com.urgenciasYa.application.controller.interfaces.IModelEps;
import com.urgenciasYa.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.application.dto.response.EpsResponseDTO;
import com.urgenciasYa.application.service.IModel.IEpsModel;
import com.urgenciasYa.infrastructure.handleError.SuccessResponse;
import com.urgenciasYa.application.exceptions.ErrorSimple;
import com.urgenciasYa.domain.model.Eps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/eps")
@CrossOrigin(origins = "https://urgenciasya-frontend-3.onrender.com")
public class EpsController implements IModelEps {

    @Autowired
    IEpsModel epsService;

    @GetMapping("/getAll")
    @Operation(
            summary = "Retrieves a list of all EPS entities",
            description = "Returns a list of all EPS entities available in the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List obtained successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = EpsResponseDTO.class,
                                    example = "[{\"name\": \"EPS Name 1\"}, {\"name\": \"EPS Name 2\"}]"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No EPS entities found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 404, \"status\": \"NOT_FOUND\", \"message\": \"No EPS entities found\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 500, \"status\": \"INTERNAL_SERVER_ERROR\", \"message\": \"An unexpected error occurred.\"}"
                            )
                    )
            )
    })
    public ResponseEntity<List<EpsResponseDTO>> getAllEps() {
        try {
            List<Eps> epss = epsService.readALl();

            if (epss.isEmpty()) {
                ErrorSimple errorSimple = ErrorSimple.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND.name())
                        .message("No EPS entities found")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
            }

            List<EpsResponseDTO> epsResponseDTO = epss.stream()
                    .map(eps -> EpsResponseDTO.builder()
                            .name(eps.getName())
                            .build())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(epsResponseDTO);
        } catch (Exception e) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve an EPS entity by ID",
            description = "Returns the EPS entity identified by the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "EPS entity found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = EpsResponseDTO.class,
                                    example = "{\"name\": \"EPS Name\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found, if the EPS entity with the given ID does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 404, \"status\": \"NOT_FOUND\", \"message\": \"EPS entity with ID {id} not found.\"}"
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
                                    example = "{\"code\": 500, \"status\": \"INTERNAL_SERVER_ERROR\", \"message\": \"An unexpected error occurred.\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Eps eps = epsService.getById(id);
            EpsResponseDTO epsResponseDTO = EpsResponseDTO.builder()
                    .name(eps.getName())
                    .build();
            return ResponseEntity.ok(epsResponseDTO);
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
                    .message("An unexpected error occurred: " + exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }


    @PostMapping
    @Operation(
            summary = "Create a new EPS entity",
            description = "Creates a new EPS entity with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "EPS entity created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = SuccessResponse.class,
                                    example = "{\"code\": 201, \"status\": \"CREATED\", \"message\": \"EPS created successfully\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request, if the request body is invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 400, \"status\": \"BAD_REQUEST\", \"message\": \"Invalid request body\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict, if an EPS entity with the same details already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 409, \"status\": \"CONFLICT\", \"message\": \"An EPS entity with the same details already exists\"}"
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
                                    example = "{\"code\": 500, \"status\": \"INTERNAL_SERVER_ERROR\", \"message\": \"An unexpected error occurred\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> create(@RequestBody @Valid EpsRequestDTO dto) {
        try {
            epsService.create(dto);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .status(HttpStatus.CREATED.name())
                    .message("EPS created successfully")
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
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }



    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Delete an EPS entity by ID",
            description = "Deletes the EPS entity identified by the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content, EPS entity deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found, if the EPS entity with the given ID does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 404, \"status\": \"NOT_FOUND\", \"message\": \"EPS entity with ID {id} not found\"}"
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
                                    example = "{\"code\": 500, \"status\": \"INTERNAL_SERVER_ERROR\", \"message\": \"An unexpected error occurred.\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            epsService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("EPS entity with ID " + id + " not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred: " + exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing EPS entity",
            description = "Updates the EPS entity identified by the given ID with the provided details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "EPS entity updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = SuccessResponse.class,
                                    example = "{\"code\": 200, \"status\": \"OK\", \"message\": \"EPS entity updated successfully\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request, if the request body is invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 400, \"status\": \"BAD_REQUEST\", \"message\": \"Invalid request body\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found, if the EPS entity with the given ID does not exist",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 404, \"status\": \"NOT_FOUND\", \"message\": \"EPS entity with ID {id} not found\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict, if there is a conflict with the current state of the resource",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorSimple.class,
                                    example = "{\"code\": 409, \"status\": \"CONFLICT\", \"message\": \"Conflict with the current state of the resource\"}"
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
                                    example = "{\"code\": 500, \"status\": \"INTERNAL_SERVER_ERROR\", \"message\": \"An unexpected error occurred.\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> update(@RequestBody @Valid Eps eps, @PathVariable Integer id) {
        try {
            epsService.update(id, eps);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("EPS entity updated successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (IllegalArgumentException exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND.name())
                    .message("EPS entity with ID " + id + " not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimple);
        } catch (Exception exception) {
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message("An unexpected error occurred: " + exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }
    }

}
