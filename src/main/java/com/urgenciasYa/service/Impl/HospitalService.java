package com.urgenciasYa.service.Impl;

import com.urgenciasYa.dto.request.HospitalSearchRequestDTO;
import com.urgenciasYa.dto.response.HospitalCardDTO;
import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import com.urgenciasYa.model.Eps;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    private static final double EARTH_RADIUS = 6371;

    public List<HospitalCardDTO> getHospitalsNearby(HospitalSearchRequestDTO requestDTO) {
        List<Hospital> hospitals = hospitalRepository.findByEpsAndTown(requestDTO.getEps(), requestDTO.getTown());

        hospitals.sort((h1, h2) -> {
            double distanceToH1 = calculateDistance(requestDTO.getUserLatitude(), requestDTO.getUserLongitude(), h1.getLatitude(), h1.getLongitude());
            double distanceToH2 = calculateDistance(requestDTO.getUserLatitude(), requestDTO.getUserLongitude(), h2.getLatitude(), h2.getLongitude());
            return Double.compare(distanceToH1, distanceToH2);
        });

        return hospitals.stream().map(hospital -> HospitalCardDTO.builder()
                .url_image(hospital.getUrl_image())
                .phone_number(hospital.getPhone_number())
                .name(hospital.getName())
                .rating(hospital.getRating())
                .howtogetthere(hospital.getHowtogetthere())
                .nameTown(hospital.getTown_id().getName())
                .nameEps(hospital.getEps_id().stream().findFirst().map(Eps::getName).orElse(""))
                .build()).collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
