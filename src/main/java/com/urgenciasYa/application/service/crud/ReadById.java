package com.urgenciasYa.application.service.crud;

/*
 * Generic interface for reading an entity by its ID.
 */

public interface ReadById <Entity, ID> {

    //Retrieves an entity by its ID.

    Entity getById(ID id); // Method to get an entity by its ID
}
