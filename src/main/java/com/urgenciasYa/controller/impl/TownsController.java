package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelTowns;
import com.urgenciasYa.dto.request.TownCreateDTO;
import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.service.IModel.ITownsModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/town")
public class TownsController implements IModelTowns {

    @Autowired
    ITownsModel townsService;

    @Override
    @Operation( summary = "Gets a list of all available cities")
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
    public ResponseEntity<String> create(TownCreateDTO dto) {
        try{
            townsService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
        }catch (IllegalArgumentException e){
            Map<String, String> errorResponse = Collections.singletonMap("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse.toString());
        }catch (Exception e){
            Map<String, String> errorResponse = Collections.singletonMap("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }
}
