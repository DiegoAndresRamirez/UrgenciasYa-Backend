package com.urgenciasYa.controller.generic;

import org.springframework.http.ResponseEntity;

public interface Update <Entity,ID>{
   ResponseEntity<?> update(Entity entity,ID id);
}
