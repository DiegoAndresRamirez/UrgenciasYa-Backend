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
@CrossOrigin(origins = "http://localhost:3000")
public class EpsController implements IModelEps {

    @Autowired
    IEpsModel epsService;

    @GetMapping
    @Operation(
            summary = "Retrieves a list of all EPS entities.",
            description = "Returns a list of all EPS entities available in the system."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List obtained successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EpsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No EPS entities found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorSimple.class)))
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

    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid EpsRequestDTO dto) {
        try {
            epsService.create(dto);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .status(HttpStatus.CREATED.name())
                    .message("Eps creada con exito")
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
    @Override
    public ResponseEntity<?> delete(Integer integer) {
        return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid Eps eps,@PathVariable Integer id) {
        try {
            epsService.update(id, eps);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("Eps actualizada")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        }catch (IllegalArgumentException exception){
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.CONFLICT.value())
                    .status(HttpStatus.CONFLICT.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorSimple);
        }catch (Exception exception){
            ErrorSimple errorSimple = ErrorSimple.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimple);
        }

    }
}
