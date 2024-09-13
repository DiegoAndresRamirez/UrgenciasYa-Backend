package com.urgenciasYa.service.crud;

public interface CreateDTO <EntityRequest,Entity> {
    Entity create(EntityRequest entity);
}
