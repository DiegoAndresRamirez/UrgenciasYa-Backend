package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.Update;
import com.urgenciasYa.application.dto.request.TownCreateDTO;
import com.urgenciasYa.domain.model.Towns;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelTowns extends Create<TownCreateDTO>, Delete<Integer> , Update<Integer, Towns>{
    public ResponseEntity<?> getAllTowns();}

