package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.Update;
import com.urgenciasYa.application.dto.request.TownCreateDTO;
import com.urgenciasYa.application.dto.response.TownsDTO;
import com.urgenciasYa.domain.model.Towns;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelTowns extends Create<TownCreateDTO>, Update<Towns,Integer>, Delete<Integer> {
    public ResponseEntity<List<TownsDTO>> getAllTowns();}

