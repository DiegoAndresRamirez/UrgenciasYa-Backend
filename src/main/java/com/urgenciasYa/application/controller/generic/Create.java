package com.urgenciasYa.application.controller.generic;

import org.springframework.http.ResponseEntity;

public interface Create <D> {
    ResponseEntity<?> create(D dto);
}
