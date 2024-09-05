package com.urgenciasYa.repository;

import com.urgenciasYa.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Integer, Hospital> {

    @Query("SELECT url_image, phone_number, name, rating, howtogetthere, e.name, t.name FROM Hospital h " +
            "inner join eps e "+
            "inner join towns t "+
            "where e.name = epsName " +
            "and t.name = townName")
    List<Hospital> findByEpsAndTown(@Param("epsName") String epsName, @Param("townName") String townName);

}
