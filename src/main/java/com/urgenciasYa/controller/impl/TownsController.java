package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.handleError.SuccessResponse;
import com.urgenciasYa.controller.interfaces.IModelTowns;
import com.urgenciasYa.dto.request.TownCreateDTO;
import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.exceptions.ErrorSimple;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.service.IModel.ITownsModel;
import io.swagger.v3.oas.annotations.Operation;
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
public class TownsController implements IModelTowns {

    @Autowired
    ITownsModel townsService;

    @Override
    @Operation(summary = "Gets a list of all available cities")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List obtained successfully"),
            @ApiResponse(responseCode = "404", description = "No cities found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<TownsDTO>> getAllTowns() {
        List<Towns> towns = townsService.readALl();
        List<TownsDTO> townsDTOS = towns.stream()
                .map(town -> TownsDTO.builder()
                        .name(town.getName())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(townsDTOS);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TownCreateDTO dto) {
        try {
            townsService.create(dto);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.CREATED.value())
                    .status(HttpStatus.CREATED.name())
                    .message("Municipio creado con exito")
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


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid Towns towns) {
        try {
            townsService.update(towns,id);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("Municipio actualizado")
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

    @Override
    public ResponseEntity<?> update(Towns towns, Integer integer) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable Integer id) {
        try{
            townsService.delete(id);
            SuccessResponse successResponse = SuccessResponse.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK.name())
                    .message("Usuario borrado con exito")
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


