package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.service.IModel.IShiftModel;
import com.urgenciasYa.domain.model.Eps;
import com.urgenciasYa.domain.model.Hospital;
import com.urgenciasYa.domain.model.Shift;
import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.persistence.EpsRepository;
import com.urgenciasYa.infrastructure.persistence.HospitalRepository;
import com.urgenciasYa.infrastructure.persistence.ShiftRepository;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import com.urgenciasYa.common.utils.enums.StatusShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

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

    @Autowired
    private HospitalService hospitalService;

    @Override
    public Shift createShift(String document, Long hospitalId, Integer epsId) throws Exception {
        UserEntity user = userRepository.findByDocument(document);
        if (user == null) {
            throw new Exception("User not found");
        }

        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new Exception("Hospital not found"));
        Eps eps = epsRepository.findById(epsId).orElseThrow(() -> new Exception("EPS not found"));

        // Obtener el concurrency profile utilizando el nuevo método, sin latitud y longitud
        Map<String, Integer> concurrencyProfile = hospitalService.getConcurrencyProfileByHospital(hospitalId, eps.getName(), hospital.getTown_id().getName(), null, null);

        // Obtener la hora actual y el valor correspondiente
        LocalDateTime now = LocalDateTime.now();
        String hourKey = String.format("%02d", now.getHour());
        Integer value = concurrencyProfile.get(hourKey);

        // Generar shiftNumber
        char letter = (char) ('A' + value / 10);
        String number = String.format("%02d", value % 10);
        String shiftNumber = letter + number;

        // Estimar el tiempo del turno
        LocalDateTime estimatedTime = now.plusHours(1);

        Shift shift = new Shift();
        shift.setShiftNumber(shiftNumber);
        shift.setEstimatedTime(estimatedTime);
        shift.setStatus(StatusShift.PENDING);
        shift.setUser(user);
        shift.setHospital(hospital);
        shift.setEps(eps);

        return shiftRepository.save(shift);
    }
}
