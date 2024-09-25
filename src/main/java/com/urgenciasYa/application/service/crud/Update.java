package com.urgenciasYa.application.service.crud;

/*
 * Generic interface for updating an entity.
 */

public interface Update <ID, Entity> {

    //Updates an existing entity with new data.

    Entity update(ID id, Entity entity); // Method to update an entity by its ID
}
