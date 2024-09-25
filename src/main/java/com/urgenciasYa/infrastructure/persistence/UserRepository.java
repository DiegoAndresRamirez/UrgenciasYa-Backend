package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * UserRepository is an interface that extends JpaRepository to provide CRUD operations
 * for the UserEntity, representing users in the system.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    //Retrieves a UserEntity by its email address.
    UserEntity findByEmail(String email);

    //Retrieves a UserEntity by its document identifier.
    UserEntity findByDocument(String document);

    //Retrieves a UserEntity by its name.
    UserEntity findByName(String name);
}
