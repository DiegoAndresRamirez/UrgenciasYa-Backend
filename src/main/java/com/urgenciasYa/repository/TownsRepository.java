package com.urgenciasYa.repository;

import com.urgenciasYa.model.Towns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownsRepository extends JpaRepository<Towns,Integer> {
    Boolean existsByName(String name);
}
