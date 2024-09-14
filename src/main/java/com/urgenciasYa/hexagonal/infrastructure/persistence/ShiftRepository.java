package com.urgenciasYa.hexagonal.infrastructure.persistence;

import com.urgenciasYa.hexagonal.domain.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
