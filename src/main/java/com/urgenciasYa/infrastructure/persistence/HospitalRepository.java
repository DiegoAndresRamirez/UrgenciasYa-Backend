package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
 * HospitalRepository is an interface that extends JpaRepository to provide CRUD operations
 * for the Hospital entity, representing hospitals in the system.
 */

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    /*
     * Finds hospitals that are associated with a specific EPS (health insurance company)
     * and located in a specified town.
     */
    @Query("SELECT h FROM Hospital h JOIN h.eps_id he JOIN he.eps e JOIN h.town_id t WHERE e.name = :epsName AND t.name = :townName")
    List<Hospital> findByEpsNameAndTown(@Param("epsName") String epsName, @Param("townName") String townName);

    //Finds hospitals associated with a specific EPS (health insurance company).
    @Query("SELECT h FROM Hospital h JOIN h.eps_id he WHERE he.eps.name = :epsName")
    List<Hospital> findByEpsName(@Param("epsName") String epsName);

}
