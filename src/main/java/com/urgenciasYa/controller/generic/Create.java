package com.urgenciasYa.controller.generic;

import com.urgenciasYa.dto.request.UserRegisterDTO;
import org.springframework.http.ResponseEntity;

public interface Create <T,D> {
    public ResponseEntity<T> create(D dto);
}
