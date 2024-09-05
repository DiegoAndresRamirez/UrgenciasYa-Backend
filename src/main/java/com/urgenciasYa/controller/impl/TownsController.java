package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelTowns;
import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.service.IModel.ITownsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/towns")
public class TownsController implements IModelTowns {

    @Autowired
    ITownsModel townsService;

    @Override
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
}
