package com.urgenciasYa.hexagonal.application.service.crud;

public interface ReadById <Entity, ID> {
    Entity getById(ID id);
}
