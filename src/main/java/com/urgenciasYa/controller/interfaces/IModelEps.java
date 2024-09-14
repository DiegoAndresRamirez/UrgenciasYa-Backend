package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.controller.generic.Create;
import com.urgenciasYa.controller.generic.Delete;
import com.urgenciasYa.controller.generic.Update;
import com.urgenciasYa.dto.request.EpsRequestDTO;
import com.urgenciasYa.dto.request.TownCreateDTO;
import com.urgenciasYa.dto.response.EpsResponseDTO;
import com.urgenciasYa.model.Eps;
import com.urgenciasYa.model.Towns;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelEps extends Create<EpsRequestDTO>, Update<Eps,Integer>, Delete<Integer> {
    public ResponseEntity<List<EpsResponseDTO>> getAllEps();
}
