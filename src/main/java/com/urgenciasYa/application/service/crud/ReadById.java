package com.urgenciasYa.application.service.crud;

public interface ReadById <Entity, ID> {
    Entity getById(ID id);
}
