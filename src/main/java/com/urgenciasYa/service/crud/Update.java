package com.urgenciasYa.service.crud;

public interface Update <Entity,ID> {
    Entity update (Entity entity,ID id);
}
