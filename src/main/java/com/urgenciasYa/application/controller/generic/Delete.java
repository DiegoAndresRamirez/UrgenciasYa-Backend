package com.urgenciasYa.application.controller.generic;

import org.springframework.http.ResponseEntity;

// A generic interface for deleting resources in the application.
public interface Delete <ID> {

    //Deletes a resource identified by the given ID.
    ResponseEntity<?> delete(ID id);
}
