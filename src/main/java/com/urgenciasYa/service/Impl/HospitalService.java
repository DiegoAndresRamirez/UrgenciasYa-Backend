package com.urgenciasYa.service.Impl;

import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.dto.request.HospitalSearchRequestDTO;
import com.urgenciasYa.dto.response.HospitalCardDTO;
import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.repository.HospitalRepository;
import com.urgenciasYa.service.IModel.IHospitalModel;
import com.urgenciasYa.utils.ConcurrencyAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.urgenciasYa.model.Eps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HospitalService implements IHospitalModel {

    @Autowired
    private HospitalRepository hospitalRepository;

    private static final double EARTH_RADIUS = 6371;

    public List<HospitalCardDTO> getHospitalsNearby(HospitalSearchRequestDTO requestDTO) {
        String eps = requestDTO.getEps();
        String town = requestDTO.getTown();
        double userLatitude = requestDTO.getLatitude();
        double userLongitude = requestDTO.getLongitude();

        List<Hospital> hospitals = hospitalRepository.findByEpsAndTown(eps, town);

        hospitals.sort((h1, h2) -> {
            double distanceToH1 = calculateDistance(userLatitude, userLongitude, h1.getLatitude(), h1.getLongitude());
            double distanceToH2 = calculateDistance(userLatitude, userLongitude, h2.getLatitude(), h2.getLongitude());
            return Double.compare(distanceToH1, distanceToH2);
        });

        return hospitals.stream().map(hospital -> {
            Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                    hospital.getMorning_peak(),
                    hospital.getAfternoon_peak(),
                    hospital.getNight_peak()
            );

            return HospitalCardDTO.builder()
                    .url_image(hospital.getUrl_image())
                    .phone_number(hospital.getPhone_number())
                    .name(hospital.getName())
                    .rating(hospital.getRating())
                    .howtogetthere(hospital.getHowtogetthere())
                    .nameTown(hospital.getTown_id().getName())
                    .nameEps(hospital.getEps_id().stream().findFirst().map(Eps::getName).orElse(""))
                    .concurrencyProfile(concurrencyProfile)
                    .build();
        }).collect(Collectors.toList());
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

    @Override
    public Hospital create(HospitalCreateResponseDTO dto) {
        Hospital hospital = Hospital.builder()
                .url_image(dto.getUrl_image())
                .phone_number(dto.getPhone_number())
                .name(dto.getName())
                .rating(dto.getRating())
                .morning_peak(dto.getMorning_peak())
                .afternoon_peak(dto.getAfternoon_peak())
                .night_peak(dto.getNight_peak())
                .howtogetthere(dto.getHowtogetthere())
                .town_id(dto.getTown_id())
                .eps_id(dto.getEps_id())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

        return hospitalRepository.save(hospital);
    }

    @Override
    public Hospital update(Long aLong, HospitalCreateResponseDTO dto) {
        Hospital existingHospital = hospitalRepository.findById(aLong).orElseThrow(() -> new RuntimeException("Hospital with id " + aLong + " not found"));

        Hospital updatedHospital = Hospital.builder()
                .id(existingHospital.getId())
                .url_image(dto.getUrl_image())
                .phone_number(dto.getPhone_number())
                .name(dto.getName())
                .rating(dto.getRating())
                .morning_peak(dto.getMorning_peak())
                .afternoon_peak(dto.getAfternoon_peak())
                .night_peak(dto.getNight_peak())
                .howtogetthere(dto.getHowtogetthere())
                .town_id(dto.getTown_id())
                .eps_id(dto.getEps_id())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

        return hospitalRepository.save(updatedHospital);
    }

    @Override
    public void delete(Long id) {
        Hospital hospitalExist = this.hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospital with id " + id + " not found"));
        if(hospitalExist != null){
            hospitalRepository.deleteById(hospitalExist.getId());
        }else{
            throw new RuntimeException("Hospital with id " + id + " not found");
        }
    }
}