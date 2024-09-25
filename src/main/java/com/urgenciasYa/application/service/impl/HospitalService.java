package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.HospitalSearchRequestDTO;
import com.urgenciasYa.application.dto.response.*;
import com.urgenciasYa.application.mappers.HospitalMapper;
import com.urgenciasYa.application.mappers.HospitalHelperMapper;
import com.urgenciasYa.domain.model.Hospital;
import com.urgenciasYa.domain.model.HospitalEps;
import com.urgenciasYa.domain.model.Towns;
import com.urgenciasYa.domain.model.keys.HospitalEpsId;
import com.urgenciasYa.infrastructure.persistence.EpsRepository;
import com.urgenciasYa.infrastructure.persistence.HospitalEpsRepository;
import com.urgenciasYa.infrastructure.persistence.HospitalRepository;
import com.urgenciasYa.application.service.IModel.IHospitalModel;
import com.urgenciasYa.common.utils.ConcurrencyAlgorithm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.urgenciasYa.domain.model.Eps;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HospitalService implements IHospitalModel { // Implementing the IHospitalModel interface

    @Autowired
    private HospitalRepository hospitalRepository; // Injects the Hospital repository to interact with the database

    @Autowired
    private HospitalEpsRepository hospitalEpsRepository; // Injects the HospitalEps repository for managing hospital-EPS relationships

    @Autowired
    private EpsRepository epsRepository; // Injects the EPS repository to fetch EPS details

    @Autowired
    private HospitalMapper hospitalMapper; // Injects the mapper to convert entities to DTOs and vice versa

    @Autowired
    private HospitalHelperMapper hospitalHelperMapper; // Injects a helper mapper for additional hospital mapping operations

    private static final double EARTH_RADIUS = 6371; // Earth's radius in kilometers for distance calculation
    private static final double SEARCH_RADIUS = 3.0; // Maximum search radius in kilometers for nearby hospitals

    // Method to find hospitals nearby based on EPS, town, and user location
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
                .map(this::mapToHospitalCardDTO) // Convert each hospital to a DTO
                .collect(Collectors.toList());
    }

    // Method to map Hospital to HospitalCardDTO

    private HospitalCardDTO mapToHospitalCardDTO(Hospital hospital) {
        Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                hospital.getMorning_peak(),
                hospital.getAfternoon_peak(),
                hospital.getNight_peak()
        ); // Generate concurrency profile based on peak hours

        HospitalCardDTO cardDTO = hospitalHelperMapper.hospitalToHospitalCardDTO(hospital); // Convert hospital to DTO
        cardDTO.setConcurrencyProfile(concurrencyProfile); // Add concurrency profile to DTO
        return cardDTO;
    }

    // Calculates the distance between two geographical points using the Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1); // Latitude difference in radians
        double dLon = Math.toRadians(lon2 - lon1); // Longitude difference in radians
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2); // Haversine formula calculation
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // More calculations based on Haversine formula
        return EARTH_RADIUS * c; // Return distance in kilometers

    }

    // Creates a new hospital and its associated EPS records
    @Override

    public Hospital create(HospitalCreateResponseDTO dto) {
        Hospital hospital = hospitalHelperMapper.toHospital(dto); // Convert DTO to Hospital entity
        Hospital savedHospital = hospitalRepository.save(hospital); // Save the hospital in the database

        // Creating relationships between Hospital and EPS
        List<HospitalEps> hospitalEpsList = dto.getEps_id().stream()
                .map(eps -> {
                    Eps epsEntity = epsRepository.findById(eps.getId())
                            .orElseThrow(() -> new RuntimeException("EPS not found with ID: " + eps.getId())); // Fetch EPS entity or throw an error if not found
                    return new HospitalEps(new HospitalEpsId(savedHospital.getId(), eps.getId()), savedHospital, epsEntity); // Create a HospitalEps record
                })
                .collect(Collectors.toList());

        hospitalEpsRepository.saveAll(hospitalEpsList); // Save the hospital-EPS associations
        return savedHospital;
    }

    // Updates an existing hospital and its associated EPS records
    @Override
    public Hospital update(Long id, HospitalCreateResponseDTO dto) { // Method to update an existing Hospital
        Hospital existingHospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital with id " + id + " not found")); // Fetch existing hospital or throw error

        hospitalHelperMapper.hospitalCreateResponseDTOtoHospital(dto, existingHospital); // Update hospital attributes
        Hospital updatedHospital = hospitalRepository.save(existingHospital); // Save the updated hospital

        Set<HospitalEps> existingHospitalEps = new HashSet<>(hospitalEpsRepository.findAllByHospitalId(id)); // Fetch current EPS associations

        Set<HospitalEps> newHospitalEps = dto.getEps_id().stream()
                .map(eps -> {
                    Eps epsEntity = epsRepository.findById(eps.getId())
                            .orElseThrow(() -> new RuntimeException("EPS not found with ID: " + eps.getId())); // Fetch EPS entity or throw an error
                    return new HospitalEps(new HospitalEpsId(id, eps.getId()), updatedHospital, epsEntity); // Create new HospitalEps record
                })
                .collect(Collectors.toSet());

        existingHospitalEps.removeAll(newHospitalEps); // Remove old associations
        hospitalEpsRepository.deleteAll(existingHospitalEps); // Delete old EPS records
        hospitalEpsRepository.saveAll(newHospitalEps); // Save new EPS records

        return updatedHospital;
    }

    // Deletes a hospital by its ID
    @Override
    public void delete(Long id) {
        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital with id " + id + " not found"); // Throw error if hospital not found
        }
        hospitalRepository.deleteById(id); // Delete the hospital
    }

    // Fetches a hospital by its ID and returns it as a DTO
    @Override
    public HospitalGetResponseDTO getById(Long id) {
        Hospital hospitalExists = hospitalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hospital with id " + id + " not found")); // Fetch hospital or throw an error

        Towns townExist = hospitalExists.getTown_id(); // Fetch town associated with the hospital
        TownsDTO town = TownsDTO.builder()
                .name(townExist.getName())
                .build(); // Convert town entity to DTO

        List<String> epsName = hospitalExists.getEps_id().stream()
                .map(hospitalEps -> hospitalEps.getEps().getName())
                .collect(Collectors.toList()); // Collect EPS names associated with the hospital

        Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                hospitalExists.getMorning_peak(),
                hospitalExists.getAfternoon_peak(),
                hospitalExists.getNight_peak()
        ); // Generate concurrency profile

        HospitalGetResponseDTO hospitalGetResponseDTO = hospitalMapper.toHospitalGetResponseDTO(hospitalExists); // Map hospital to DTO
        hospitalGetResponseDTO.setEps_id(epsName); // Set EPS list in DTO
        hospitalGetResponseDTO.setConcurrencyProfile(concurrencyProfile); // Set concurrency profile in DTO
        hospitalGetResponseDTO.setTown_id(town); // Set town in DTO

        return hospitalGetResponseDTO;
    }

    // Fetches all hospitals and returns them as a list of DTOs
    @Override
    public List<HospitalGetResponseDTO> readALl() {
        List<Hospital> hospitalExists = this.hospitalRepository.findAll(); // Fetch all hospitals

        return hospitalExists.stream()
                .map(hospital -> {
                    Towns townExist = hospital.getTown_id(); // Fetch associated town
                    TownsDTO townsDTO = TownsDTO.builder()
                            .name(townExist.getName())
                            .build(); // Convert town entity to DTO

                    Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                            hospital.getMorning_peak(),
                            hospital.getAfternoon_peak(),
                            hospital.getNight_peak()
                    ); // Generate concurrency profile

                    HospitalGetResponseDTO hospitalGetResponseDTO = hospitalMapper.toHospitalGetResponseDTO(hospital); // Convert hospital to DTO
                    hospitalGetResponseDTO.setTown_id(townsDTO); // Set town in DTO
                    hospitalGetResponseDTO.setConcurrencyProfile(concurrencyProfile); // Set concurrency profile in DTO

                    return hospitalGetResponseDTO;
                })
                .collect(Collectors.toList()); // Return list of DTOs
    }

    // Retrieves the concurrency profile for a hospital based on its ID and other parameters
    public Map<String, Integer> getConcurrencyProfileByHospital(Long hospitalId, String eps, String town, Double latitude, Double longitude) {
        HospitalSearchRequestDTO requestDTO = new HospitalSearchRequestDTO(eps, town, latitude, longitude); // Create a request DTO for searching hospitals
        List<HospitalCardDTO> hospitals = getHospitalsNearby(requestDTO); // Get nearby hospitals

        return hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId)) // Filter the hospital by ID
                .findFirst()
                .map(HospitalCardDTO::getConcurrencyProfile)
                .orElseThrow(() -> new EntityNotFoundException("Hospital with id " + hospitalId + " not found")); // Throw error if not found
    }
}
