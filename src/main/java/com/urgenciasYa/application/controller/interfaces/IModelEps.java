package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.Create;
import com.urgenciasYa.application.controller.generic.Delete;
import com.urgenciasYa.application.controller.generic.Update;
import com.urgenciasYa.application.dto.request.EpsRequestDTO;
import com.urgenciasYa.application.dto.response.EpsResponseDTO;
import com.urgenciasYa.domain.model.Eps;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelEps extends Create<EpsRequestDTO>, Update<Eps,Integer>, Delete<Integer> {
    public ResponseEntity<List<EpsResponseDTO>> getAllEps();
}
