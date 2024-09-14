package com.urgenciasYa.repository;

import com.urgenciasYa.hexagonal.domain.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital ,Long> {

    @Query("SELECT h FROM Hospital h JOIN h.eps_id e JOIN h.town_id t WHERE e.name = :epsName AND t.name = :townName")
    List<Hospital> findByEpsAndTown(@Param("epsName") String epsName, @Param("townName") String townName);

}
