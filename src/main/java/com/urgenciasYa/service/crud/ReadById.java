package com.urgenciasYa.service.crud;

import java.util.Optional;

public interface ReadById <Entity, ID> {
    Optional<Entity> getById(ID id);
}
