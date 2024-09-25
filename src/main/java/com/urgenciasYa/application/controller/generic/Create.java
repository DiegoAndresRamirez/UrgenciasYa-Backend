package com.urgenciasYa.application.controller.generic;

import org.springframework.http.ResponseEntity;

//  A generic interface for creating resources in the application.

public interface Create <D> {

    // Creates a new resource using the provided DTO.
    ResponseEntity<?> create(D dto);
}
