package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.HospitalSearchRequestDTO;
import com.urgenciasYa.application.dto.response.*;
import com.urgenciasYa.domain.model.Hospital;
import com.urgenciasYa.domain.model.HospitalEps;
import com.urgenciasYa.domain.model.Towns;
import com.urgenciasYa.domain.model.keys.HospitalEpsId;
import com.urgenciasYa.infrastructure.persistence.EpsRepository;
import com.urgenciasYa.infrastructure.persistence.HospitalEpsRepository;
import com.urgenciasYa.infrastructure.persistence.HospitalRepository;
import com.urgenciasYa.application.service.IModel.IHospitalModel;
import com.urgenciasYa.common.utils.ConcurrencyAlgorithm;
import com.urgenciasYa.infrastructure.persistence.TownsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.urgenciasYa.domain.model.Eps;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HospitalService implements IHospitalModel { // Implementing the IHospitalModel interface

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalEpsRepository hospitalEpsRepository;

    @Autowired
    private TownsRepository townsRepository;

    @Autowired
    private EpsRepository epsRepository;

    private static final double EARTH_RADIUS = 6371; // Earth radius in kilometers
    private static final double SEARCH_RADIUS = 3.0; // Search radius in kilometers

    // Method to retrieve hospitals nearby based on user location and EPS
    public List<HospitalCardDTO> getHospitalsNearby(HospitalSearchRequestDTO requestDTO) {
        String epsName = requestDTO.getEps(); // Getting EPS name from request
        String townName = requestDTO.getTown(); // Getting town name from request
        Double userLatitude = requestDTO.getLatitude(); // Getting user latitude from request
        Double userLongitude = requestDTO.getLongitude(); // Getting user longitude from request

        List<Hospital> hospitals; // List to store hospitals

        if (userLatitude != null && userLongitude != null) { // If user location is provided
            hospitals = hospitalRepository.findByEpsName(epsName);  // Find hospitals by EPS name
            hospitals = hospitals.stream()
                    .filter(h -> calculateDistance(userLatitude, userLongitude, h.getLatitude(), h.getLongitude()) <= SEARCH_RADIUS) // Filter by distance
                    .sorted(Comparator.comparingDouble(h -> calculateDistance(userLatitude, userLongitude, h.getLatitude(), h.getLongitude()))) // Sort by distance
                    .collect(Collectors.toList()); // Collect the results
        } else { // If user location is not provided
            hospitals = townName != null ? hospitalRepository.findByEpsNameAndTown(epsName, townName) : hospitalRepository.findByEpsName(epsName); // Find hospitals by town or EPS name
        }

        // Map the Hospital objects to HospitalCardDTO and return
        return hospitals.stream()
                .map(this::mapToHospitalCardDTO)
                .collect(Collectors.toList());
    }

    // Method to map Hospital to HospitalCardDTO
    private HospitalCardDTO mapToHospitalCardDTO(Hospital hospital) {
        Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                hospital.getMorning_peak(),
                hospital.getAfternoon_peak(),
                hospital.getNight_peak()
        ); // Generate concurrency profile based on peak hours

        return HospitalCardDTO.builder() // Building HospitalCardDTO
                .id(hospital.getId())
                .url_image(hospital.getUrl_image())
                .phone_number(hospital.getPhone_number())
                .name(hospital.getName())
                .rating(hospital.getRating())
                .howtogetthere(hospital.getHowtogetthere())
                .nameTown(hospital.getTown_id().getName())
                .nameEps(hospital.getEps_id().stream().map(hospitalEps -> hospitalEps.getEps().getName()).collect(Collectors.joining(", ")))
                .concurrencyProfile(concurrencyProfile)
                .latitude(hospital.getLatitude())
                .longitude(hospital.getLongitude())
                .build();
    }

    // Method to calculate distance between two geographical points
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1); // Latitude difference in radians
        double dLon = Math.toRadians(lon2 - lon1); // Longitude difference in radians
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2); // Haversine formula calculation
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // More calculations based on Haversine formula
        return EARTH_RADIUS * c; // Return distance in kilometers
    }

    @Override
    public Hospital create(HospitalCreateResponseDTO dto) { // Method to create a new Hospital
        Hospital hospital = Hospital.builder() // Building a new Hospital object
                .url_image(dto.getUrl_image())
                .phone_number(dto.getPhone_number())
                .name(dto.getName())
                .rating(dto.getRating())
                .morning_peak(dto.getMorning_peak())
                .afternoon_peak(dto.getAfternoon_peak())
                .night_peak(dto.getNight_peak())
                .howtogetthere(dto.getHowtogetthere())
                .town_id(dto.getTown_id())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

        Hospital savedHospital = hospitalRepository.save(hospital);

        // Creating relationships between Hospital and EPS
        List<HospitalEps> hospitalEpsList = dto.getEps_id().stream()
                .map(eps -> {
                    Eps epsEntity = epsRepository.findById(eps.getId())
                            .orElseThrow(() -> new RuntimeException("EPS not found with ID: " + eps.getId()));
                    return new HospitalEps(new HospitalEpsId(savedHospital.getId(), eps.getId()), savedHospital, epsEntity);
                })
                .collect(Collectors.toList());

        hospitalEpsRepository.saveAll(hospitalEpsList);

        return savedHospital;
    }

    @Override
    public Hospital update(Long id, HospitalCreateResponseDTO dto) { // Method to update an existing Hospital
        Hospital existingHospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital with id " + id + " not found"));  // Check if the hospital exists

        // Update hospital details
        existingHospital.setUrl_image(dto.getUrl_image());
        existingHospital.setPhone_number(dto.getPhone_number());
        existingHospital.setName(dto.getName());
        existingHospital.setRating(dto.getRating());
        existingHospital.setMorning_peak(dto.getMorning_peak());
        existingHospital.setAfternoon_peak(dto.getAfternoon_peak());
        existingHospital.setNight_peak(dto.getNight_peak());
        existingHospital.setHowtogetthere(dto.getHowtogetthere());
        existingHospital.setTown_id(dto.getTown_id());
        existingHospital.setLatitude(dto.getLatitude());
        existingHospital.setLongitude(dto.getLongitude());

        Hospital updatedHospital = hospitalRepository.save(existingHospital); // Save the updated hospital

        Set<HospitalEps> existingHospitalEps = new HashSet<>(hospitalEpsRepository.findAllByHospitalId(id)); // Get existing EPS relationships
        Set<HospitalEps> newHospitalEps = dto.getEps_id().stream()
                .map(eps -> {
                    Eps epsEntity = epsRepository.findById(eps.getId())
                            .orElseThrow(() -> new RuntimeException("EPS not found with ID: " + eps.getId()));
                    return new HospitalEps(new HospitalEpsId(id, eps.getId()), updatedHospital, epsEntity);
                })
                .collect(Collectors.toSet());

        existingHospitalEps.removeAll(newHospitalEps);
        hospitalEpsRepository.deleteAll(existingHospitalEps);

        hospitalEpsRepository.saveAll(newHospitalEps);

        return updatedHospital;
    }

    @Override
    public void delete(Long id) {
        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital with id " + id + " not found");
        }
        hospitalRepository.deleteById(id);
    }

    @Override
    public HospitalGetResponseDTO getById(Long id) {
        Hospital hospitalExists = this.hospitalRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Hospital with id " + id + " not found"));

        Towns townExist = hospitalExists.getTown_id();
        TownsDTO town = TownsDTO.builder()
                .name(townExist.getName())
                .build();

        List<HospitalEps> epsExists = hospitalExists.getEps_id();
        List<String> epsName = new ArrayList<>();
        for (HospitalEps hospitalEps : epsExists) {
            Eps epsInstance = hospitalEps.getEps();
            epsName.add(epsInstance.getName());;
        }

        Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                hospitalExists.getMorning_peak(),
                hospitalExists.getAfternoon_peak(),
                hospitalExists.getNight_peak()
        );


        HospitalGetResponseDTO hospitalGetResponseDTO = HospitalGetResponseDTO.builder()
                .id(hospitalExists.getId())
                .url_image(hospitalExists.getUrl_image())
                .phone_number(hospitalExists.getPhone_number())
                .name(hospitalExists.getName())
                .rating(hospitalExists.getRating())
                .morning_peak(hospitalExists.getMorning_peak())
                .afternoon_peak(hospitalExists.getAfternoon_peak())
                .night_peak(hospitalExists.getNight_peak())
                .howtogetthere(hospitalExists.getHowtogetthere())
                .town_id(town)
                .eps_id(epsName)
                .latitude(hospitalExists.getLatitude())
                .longitude(hospitalExists.getLongitude())
                .concurrencyProfile(concurrencyProfile)
                .build();

        return hospitalGetResponseDTO;
    }

    @Override
    public List<HospitalGetResponseDTO> readALl() {
        List<HospitalGetResponseDTO> hospitals = new ArrayList<>();
        List<Hospital> hospitalExists = this.hospitalRepository.findAll();

        hospitalExists.forEach((hospital) -> {
            Towns townExist = hospital.getTown_id();
            TownsDTO towns = TownsDTO.builder()
                    .name(townExist.getName())
                    .build();

            List<HospitalEps> epsExists = hospital.getEps_id();
            List<String> epsName = new ArrayList<>();
            for (HospitalEps hospitalEps : epsExists) {
                Eps epsInstance = hospitalEps.getEps();
                epsName.add(epsInstance.getName());;
            }

            HospitalGetResponseDTO hospitalGetResponseDTO = HospitalGetResponseDTO.builder()
                    .id(hospital.getId())
                    .url_image(hospital.getUrl_image())
                    .phone_number(hospital.getPhone_number())
                    .name(hospital.getName())
                    .rating(hospital.getRating())
                    .morning_peak(hospital.getMorning_peak())
                    .afternoon_peak(hospital.getAfternoon_peak())
                    .night_peak(hospital.getNight_peak())
                    .howtogetthere(hospital.getHowtogetthere())
                    .town_id(towns)
                    .eps_id(epsName)
                    .latitude(hospital.getLatitude())
                    .longitude(hospital.getLongitude())
                    .build();

            hospitals.add(hospitalGetResponseDTO);
        });
        return hospitals;
    }

    public Map<String, Integer> getConcurrencyProfileByHospital(Long hospitalId, String eps, String town, Double latitude, Double longitude) {
        HospitalSearchRequestDTO requestDTO = new HospitalSearchRequestDTO(eps, town, latitude, longitude);
        List<HospitalCardDTO> hospitals = getHospitalsNearby(requestDTO);

        return hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .map(HospitalCardDTO::getConcurrencyProfile)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital not found or has no concurrency profile"));
    }
}