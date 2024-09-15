package com.urgenciasYa.application.service.crud;

public interface CreateDTO <EntityRequest,Entity> {
    Entity create(EntityRequest entity);
}
