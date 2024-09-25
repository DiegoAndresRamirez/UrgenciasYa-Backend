package com.urgenciasYa.application.service.crud;

import java.util.List;

/*
 * Generic interface for reading all entities.
 */

public interface ReadAll <Entity> {

    // Retrieves a list of all entities.

    public List<Entity> readALl(); // Method to read all entities
}
