package com.urgenciasYa.controller.impl;

import com.urgenciasYa.controller.interfaces.IModelEps;
import com.urgenciasYa.model.Eps;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class EpsController implements IModelEps {
    @Override
    public ResponseEntity<List<Eps>> getAllEps() {
        return null;
    }
}
