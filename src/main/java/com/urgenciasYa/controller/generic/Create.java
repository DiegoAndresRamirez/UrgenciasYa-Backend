package com.urgenciasYa.controller.generic;

import org.springframework.http.ResponseEntity;

public interface Create <Entity> {
    public ResponseEntity<Entity> create(Entity entity);
}
