package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.controller.generic.Create;
import com.urgenciasYa.dto.request.TownCreateDTO;
import com.urgenciasYa.dto.response.TownsDTO;
import com.urgenciasYa.model.Towns;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelTowns extends Create<String, TownCreateDTO> {
    public ResponseEntity<List<TownsDTO>> getAllTowns();
}
