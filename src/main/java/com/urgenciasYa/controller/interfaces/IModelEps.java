package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.controller.generic.Create;
import com.urgenciasYa.controller.generic.Delete;
import com.urgenciasYa.controller.generic.Update;
import com.urgenciasYa.hexagonal.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.hexagonal.application.dto.response.EpsResponseDTO;
import com.urgenciasYa.model.Eps;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelEps extends Create<EpsRequestDTO>, Update<Eps,Integer>, Delete<Integer> {
    public ResponseEntity<List<EpsResponseDTO>> getAllEps();
}
