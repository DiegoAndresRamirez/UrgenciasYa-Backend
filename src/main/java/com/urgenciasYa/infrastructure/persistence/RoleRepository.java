package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * RoleRepository is an interface that extends JpaRepository to provide CRUD operations
 * for the RoleEntity, representing roles within the system.
 */

public interface RoleRepository extends JpaRepository<RoleEntity,String> {

    //Finds a role entity by its code.
    Optional<RoleEntity> findRoleByCode(String code);
}
