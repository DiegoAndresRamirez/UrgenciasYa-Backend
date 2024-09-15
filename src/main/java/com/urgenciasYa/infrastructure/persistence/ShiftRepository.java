package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
