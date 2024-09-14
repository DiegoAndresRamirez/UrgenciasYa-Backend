package com.urgenciasYa.hexagonal.application.controller.generic;

import org.springframework.http.ResponseEntity;

public interface GetById <Entity, ID>{
    ResponseEntity<Entity> getById(ID id);
}
