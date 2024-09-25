package com.urgenciasYa.application.controller.generic;

import org.springframework.http.ResponseEntity;

//A generic interface for updating a resource in the application.
public interface Update <Entity,ID>{

   //Updates a resource identified by the given ID with the provided entity data.
   ResponseEntity<?> update(Entity entity,ID id);
}
