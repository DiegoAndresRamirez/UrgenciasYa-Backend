package com.urgenciasYa.hexagonal.infrastructure.persistence;

import com.urgenciasYa.hexagonal.domain.model.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpsRepository extends JpaRepository<Eps,Integer> {
    Boolean existsByName(String name);

}
