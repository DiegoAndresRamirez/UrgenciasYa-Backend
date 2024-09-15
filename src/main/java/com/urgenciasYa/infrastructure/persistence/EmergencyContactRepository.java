package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.EmergencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyEntity, Long> {
}
