package com.urgenciasYa.application.service.crud;

/*
 * Generic interface for creating entities.
 */

public interface CreateDTO <EntityRequest,Entity> {

    //Creates a new entity based on the provided request DTO.

    Entity create(EntityRequest entity); // Method to create a new entity
}
