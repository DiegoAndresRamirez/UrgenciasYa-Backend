package com.urgenciasYa.application.controller.generic;

import org.springframework.http.ResponseEntity;


//A generic interface for retrieving all resources in the application.
public interface GetAll <Entity>{

    //Retrieves all resources of the specified entity type.
    ResponseEntity<?> getAll();
}
