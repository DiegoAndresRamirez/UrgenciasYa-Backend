package com.urgenciasYa.hexagonal.application.controller.interfaces;

import com.urgenciasYa.controller.generic.Create;
import com.urgenciasYa.controller.generic.Delete;
import com.urgenciasYa.controller.generic.Update;
import com.urgenciasYa.hexagonal.application.dto.request.TownCreateDTO;
import com.urgenciasYa.hexagonal.application.dto.response.TownsDTO;
import com.urgenciasYa.hexagonal.domain.model.Towns;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelTowns extends Create<TownCreateDTO>, Update<Towns,Integer>, Delete<Integer> {
    public ResponseEntity<List<TownsDTO>> getAllTowns();}

