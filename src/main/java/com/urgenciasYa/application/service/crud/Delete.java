package com.urgenciasYa.application.service.crud;

/*
 * Generic interface for deleting entities.
 */

public interface Delete <ID>{

    //Deletes an entity identified by the given ID.

    void delete(ID id); // Method to delete an entity by its ID
}
