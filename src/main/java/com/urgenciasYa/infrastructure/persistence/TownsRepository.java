package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.Towns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * TownsRepository is an interface that extends JpaRepository to provide CRUD operations
 * for the Towns entity, representing geographical locations or municipalities.
 */
@Repository
public interface TownsRepository extends JpaRepository<Towns,Integer> {

    //Checks if a Towns entity exists with the specified name.
    Boolean existsByName(String name);
}
