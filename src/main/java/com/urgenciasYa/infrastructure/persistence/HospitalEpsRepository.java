package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.HospitalEps;
import com.urgenciasYa.domain.model.keys.HospitalEpsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalEpsRepository extends JpaRepository<HospitalEps, HospitalEpsId> {
    List<HospitalEps> findAllByHospitalId(Long hospitalId);

}
