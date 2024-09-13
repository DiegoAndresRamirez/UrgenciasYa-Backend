package com.urgenciasYa.service.Impl;

import com.urgenciasYa.model.Eps;
import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.model.Shift;
import com.urgenciasYa.model.UserEntity;
import com.urgenciasYa.repository.EpsRepository;
import com.urgenciasYa.repository.HospitalRepository;
import com.urgenciasYa.repository.ShiftRepository;
import com.urgenciasYa.repository.UserRepository;
import com.urgenciasYa.service.IModel.IShiftModel;
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
