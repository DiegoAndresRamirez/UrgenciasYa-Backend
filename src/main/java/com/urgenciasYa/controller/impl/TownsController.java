package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelTowns;
import com.urgenciasYa.model.Towns;
import com.urgenciasYa.service.IModel.ITownsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/towns")
public class TownsController implements IModelTowns {

    @Autowired
    ITownsModel townsService;

    @Override
    @GetMapping
    public ResponseEntity<List<Towns>> getAllTowns() {
        return ResponseEntity.ok(townsService.readALl());
    }
}
