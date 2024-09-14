package com.urgenciasYa.hexagonal.application.service.impl;

import com.urgenciasYa.hexagonal.domain.model.Eps;
import com.urgenciasYa.hexagonal.domain.model.Hospital;
import com.urgenciasYa.hexagonal.domain.model.Shift;
import com.urgenciasYa.hexagonal.domain.model.UserEntity;
import com.urgenciasYa.hexagonal.infrastructure.persistence.EpsRepository;
import com.urgenciasYa.hexagonal.infrastructure.persistence.HospitalRepository;
import com.urgenciasYa.hexagonal.infrastructure.persistence.ShiftRepository;
import com.urgenciasYa.hexagonal.infrastructure.persistence.UserRepository;
import com.urgenciasYa.hexagonal.application.service.IModel.IShiftModel;
import com.urgenciasYa.utils.enums.StatusShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShiftService implements IShiftModel {


    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private EpsRepository epsRepository;

    @Override
    public Shift createShift(String document, Long hospitalId, Integer epsId) throws Exception {
        UserEntity user = userRepository.findByDocument(document);
        if (user == null) {
            throw new Exception("User not found");
        }

        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new Exception("Hospital not found"));
        Eps eps = epsRepository.findById(epsId).orElseThrow(() -> new Exception("EPS not found"));

        LocalDateTime estimatedTime = LocalDateTime.now().plusHours(1);

        Shift shift = new Shift();
        shift.setShiftNumber("S" + System.currentTimeMillis());
        shift.setEstimatedTime(estimatedTime);
        shift.setStatus(StatusShift.PENDING);
        shift.setUser(user);
        shift.setHospital(hospital);
        shift.setEps(eps);

        return shiftRepository.save(shift);
    }

}
