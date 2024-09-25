package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.EmergencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository interface for accessing EmergencyEntity data in the database
@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyEntity, Long> {
    // This interface inherits CRUD operations and query methods from JpaRepository
    // EmergencyEntity is the type of the entity and Long is the type of the entity's ID
}
