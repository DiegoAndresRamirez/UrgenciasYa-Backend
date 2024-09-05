package com.urgenciasYa.repository;

import com.urgenciasYa.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Integer, Hospital> {
    List<Hospital> findByEpsAndTown(@Param("epsName") String epsName, @Param("townName") String townName);

}
