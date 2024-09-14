package com.urgenciasYa.controller.generic;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GetAll <Entity>{
    ResponseEntity<List<Entity>> getAll();
}
