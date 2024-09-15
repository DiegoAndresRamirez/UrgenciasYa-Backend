package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT h FROM Hospital h JOIN h.eps_id he JOIN he.eps e JOIN h.town_id t WHERE e.name = :epsName AND t.name = :townName")
    List<Hospital> findByEpsNameAndTown(@Param("epsName") String epsName, @Param("townName") String townName);

}
