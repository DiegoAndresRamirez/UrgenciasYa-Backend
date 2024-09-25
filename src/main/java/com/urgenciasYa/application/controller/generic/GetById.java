package com.urgenciasYa.application.controller.generic;

import org.springframework.http.ResponseEntity;

// A generic interface for retrieving a resource by its identifier.
public interface GetById <Entity, ID>{

    //Retrieves a resource identified by the given ID.
    ResponseEntity<?> getById(ID id);
}
