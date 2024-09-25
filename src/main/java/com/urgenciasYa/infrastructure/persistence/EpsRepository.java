package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * EpsRepository is an interface that extends JpaRepository to provide CRUD operations
 * for the Eps entity, which represents a health insurance company.
 */
@Repository
public interface EpsRepository extends JpaRepository<Eps,Integer> {

    //Checks if an Eps entity exists by its name.
    Boolean existsByName(String name);

    // Finds an Eps entity by its name.
    Eps findByName(String name);

}
