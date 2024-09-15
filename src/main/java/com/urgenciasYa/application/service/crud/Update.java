package com.urgenciasYa.application.service.crud;

public interface Update <ID, Entity> {
    Entity update(ID id, Entity entity);
}
