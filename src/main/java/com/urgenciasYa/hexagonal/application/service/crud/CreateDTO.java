package com.urgenciasYa.hexagonal.application.service.crud;

public interface CreateDTO <EntityRequest,Entity> {
    Entity create(EntityRequest entity);
}
