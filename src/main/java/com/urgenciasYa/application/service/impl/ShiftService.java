package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.dto.response.RoleResponseDTO;
import com.urgenciasYa.application.dto.response.UserResponseDTO;
import com.urgenciasYa.application.dto.response.UserShiftResponseDTO;
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
import java.util.Map;
import java.util.Random;

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

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new Exception("Hospital not found"));
        Eps eps = epsRepository.findById(epsId)
                .orElseThrow(() -> new Exception("EPS not found"));

        // Get concurrency profile
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
        UserResponseDTO userDTO = UserResponseDTO.builder()
                .id(shift.getUser().getId())
                .name(shift.getUser().getName())
                .eps(shift.getUser().getEps())
                .email(shift.getUser().getEmail())
                .document(shift.getUser().getDocument())
                .emergency(shift.getUser().getEmergency() != null ? EmergencyContactRequestDTO.builder()
                        .name(shift.getUser().getEmergency().getName())
                        .phone(shift.getUser().getEmergency().getPhone())
                        .build() : null)
                .role(shift.getUser().getRole() != null ? RoleResponseDTO.builder()
                        .code(shift.getUser().getRole().getCode())
                        .build() : null)
                .build();

        return UserShiftResponseDTO.builder()
                .id(shift.getId())
                .shiftNumber(shift.getShiftNumber())
                .estimatedTime(shift.getEstimatedTime())
                .status(shift.getStatus().name())
                .user(userDTO)
                .hospitalId(shift.getHospital().getId())
                .epsId(shift.getEps().getId())
                .build();
    }

}