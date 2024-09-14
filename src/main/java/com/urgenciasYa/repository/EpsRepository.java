package com.urgenciasYa.repository;

import com.urgenciasYa.model.Eps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpsRepository extends JpaRepository<Eps,Integer> {
    Boolean existsByName(String name);

}
