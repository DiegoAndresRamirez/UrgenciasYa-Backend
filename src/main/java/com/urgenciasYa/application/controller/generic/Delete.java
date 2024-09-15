package com.urgenciasYa.application.controller.generic;

import org.springframework.http.ResponseEntity;

public interface Delete <ID> {
    ResponseEntity<?> delete(ID id);
}
