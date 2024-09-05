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

    private static final double EARTH_RADIUS = 6371;

    public List<Hospital> getHospitalsNearby(String eps, String town, double userLatitude, double userLongitude) {
        List<Hospital> hospitals = hospitalRepository.findByEpsAndTown(eps, town);

        hospitals.sort((h1, h2) -> {
            double distanceToH1 = calculateDistance(userLatitude, userLongitude, h1.getLatitude(), h1.getLongitude());
            double distanceToH2 = calculateDistance(userLatitude, userLongitude, h2.getLatitude(), h2.getLongitude());
            return Double.compare(distanceToH1, distanceToH2);
        });

        return hospitals;
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
