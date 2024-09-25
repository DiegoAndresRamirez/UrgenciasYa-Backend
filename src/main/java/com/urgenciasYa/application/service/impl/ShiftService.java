package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.response.*;
import com.urgenciasYa.application.mappers.ShiftMapper;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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

    @Autowired
    private ShiftMapper shiftMapper;

    // Method to create a shift for a user
    @Override
    public Shift createShift(String document, Long hospitalId, Integer epsId) throws Exception {
        // Retrieve the user based on their document
        UserEntity user = userRepository.findByDocument(document);
        if (user == null) {
            throw new Exception("User not found");
        }

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new Exception("Hospital not found"));
        Eps eps = epsRepository.findById(epsId)
                .orElseThrow(() -> new Exception("EPS not found"));

        // // Get the concurrency profile for the specified hospital and EPS
        Map<String, Integer> concurrencyProfile = hospitalService.getConcurrencyProfileByHospital(
                hospitalId, eps.getName(), hospital.getTown_id().getName(), null, null);

        // Check concurrency profile content
        System.out.println("Concurrency Profile: " + concurrencyProfile);

        if (concurrencyProfile.isEmpty()) {
            throw new Exception("No concurrency profile found for the specified hospital.");
        }

        LocalDateTime now = LocalDateTime.now();
        String hourKey = String.format("%02d:00", now.getHour()); // Include ':00'

        Integer value = concurrencyProfile.get(hourKey);

        if (value == null) {
            // Assign default value
            value = 0; // Handle as needed
        }

        // Generate shift number
        char letter = (char) ('A' + value / 10);
        String number = String.format("%02d", value % 10);
        String shiftNumber = letter + number;

        // Calculate estimated time with random multiplier
        int multiplier = new Random().nextInt(3) + 3; // Generate random between 3 and 5
        Duration estimatedTimeExtension = Duration.ofHours(multiplier);
        LocalDateTime estimatedTime = now.plus(estimatedTimeExtension);

        Shift shift = new Shift();
        shift.setShiftNumber(shiftNumber);
        shift.setEstimatedTime(estimatedTime);
        shift.setStatus(StatusShift.PENDING);
        shift.setUser(user);
        shift.setHospital(hospital);
        shift.setEps(eps);

        return shiftRepository.save(shift);
    }

    public UserShiftResponseDTO convertShiftToDTO(Shift shift) {

        UserShiftResponseDTO userShiftResponseDTO = shiftMapper.shiftToUserShiftResponseDTO(shift);
        userShiftResponseDTO.setHospitalId(shiftMapper.hospitalToHospitalShiftResponseDTO(shift.getHospital()));
        userShiftResponseDTO.setEpsId(shiftMapper.epsToEpsShiftResponseDTO(shift.getEps()));
        return userShiftResponseDTO;

    }
    public List<UserShiftResponseDTO> getAllShiftsByUser (String document) throws Exception {
            UserEntity user = userRepository.findByDocument(document);

            List<Shift> shifts = shiftRepository.findByUser(user);

            return shifts.stream()
                    .map(this::convertShiftToDTO)
                    .collect(Collectors.toList());
    }

    }