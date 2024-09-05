package com.urgenciasYa.service.Impl;

import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    public List<Hospital> findHospitalByEpsAndTown(String epsName, String townName){
        return hospitalRepository.findByEpsAndTown(epsName, townName);
    }
}
