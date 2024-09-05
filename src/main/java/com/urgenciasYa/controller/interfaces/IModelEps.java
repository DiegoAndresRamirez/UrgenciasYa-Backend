package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.dto.response.EpsDTO;
import com.urgenciasYa.model.Eps;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelEps {
    public ResponseEntity<List<EpsDTO>> getAllEps();
}
