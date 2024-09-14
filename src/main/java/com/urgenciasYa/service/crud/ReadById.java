package com.urgenciasYa.service.crud;

import java.util.Optional;

public interface ReadById <Entity, ID> {
    Entity getById(ID id);
}
