package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.HospitalEps;
import com.urgenciasYa.domain.model.keys.HospitalEpsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
 * HospitalEpsRepository is an interface that extends JpaRepository to provide CRUD operations
 * for the HospitalEps entity, which represents the association between hospitals and EPS (health insurance companies).
 */

@Repository
public interface HospitalEpsRepository extends JpaRepository<HospitalEps, HospitalEpsId> {

    // Finds all HospitalEps entities associated with a specific hospital.
    List<HospitalEps> findAllByHospitalId(Long hospitalId);

}
