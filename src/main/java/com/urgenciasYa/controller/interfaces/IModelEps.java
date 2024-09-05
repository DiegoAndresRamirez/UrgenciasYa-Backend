package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.model.Eps;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IModelEps {
    public ResponseEntity<List<Eps>> getAllEps();
}
