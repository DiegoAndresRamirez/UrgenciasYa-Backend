package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.common.utils.enums.StatusShift;
import com.urgenciasYa.domain.model.Shift;
import com.urgenciasYa.domain.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByUser(UserEntity user);

}
