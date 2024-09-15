package com.urgenciasYa.infrastructure.persistence;

import com.urgenciasYa.domain.model.HospitalEps;
import com.urgenciasYa.domain.model.keys.HospitalEpsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalEpsRepository extends JpaRepository<HospitalEps, HospitalEpsId> {

}
