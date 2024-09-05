package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.model.Towns;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelTowns {
    public ResponseEntity<List<Towns>> getAllCities();
}
