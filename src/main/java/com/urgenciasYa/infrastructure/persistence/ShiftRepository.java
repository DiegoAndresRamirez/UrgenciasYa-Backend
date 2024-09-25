package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.Shift;
import com.urgenciasYa.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
 * ShiftRepository is an interface that extends JpaRepository to provide CRUD operations
 * for the Shift entity, representing medical appointments or shifts for users.
 */
@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    //Finds a list of shifts associated with a specific user.
    List<Shift> findByUser(UserEntity user);

}
