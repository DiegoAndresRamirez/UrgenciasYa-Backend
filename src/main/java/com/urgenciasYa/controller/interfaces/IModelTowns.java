package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.model.Towns;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelTowns {
    public ResponseEntity<List<TownsDTO>> getAllTowns();
}
