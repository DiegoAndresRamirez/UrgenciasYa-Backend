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
import com.urgenciasYa.infrastructure.persistence.TownsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.urgenciasYa.domain.model.Eps;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HospitalService implements IHospitalModel {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalEpsRepository hospitalEpsRepository;

    @Autowired
    private EpsRepository epsRepository;

    @Autowired
    private HospitalMapper hospitalMapper;

    @Autowired
    private HospitalHelperMapper hospitalHelperMapper;

    private static final double EARTH_RADIUS = 6371;
    private static final double SEARCH_RADIUS = 3.0; // en kilómetros

    public List<HospitalCardDTO> getHospitalsNearby(HospitalSearchRequestDTO requestDTO) {
        String epsName = requestDTO.getEps();
        String townName = requestDTO.getTown();
        Double userLatitude = requestDTO.getLatitude();
        Double userLongitude = requestDTO.getLongitude();

        List<Hospital> hospitals;

        if (userLatitude != null && userLongitude != null) {
            hospitals = hospitalRepository.findByEpsName(epsName);
            hospitals = hospitals.stream()
                    .filter(h -> calculateDistance(userLatitude, userLongitude, h.getLatitude(), h.getLongitude()) <= SEARCH_RADIUS)
                    .sorted(Comparator.comparingDouble(h -> calculateDistance(userLatitude, userLongitude, h.getLatitude(), h.getLongitude())))
                    .collect(Collectors.toList());
        } else {
            hospitals = townName != null ? hospitalRepository.findByEpsNameAndTown(epsName, townName) : hospitalRepository.findByEpsName(epsName);
        }

        return hospitals.stream()
                .map(this::mapToHospitalCardDTO)
                .collect(Collectors.toList());
    }

    private HospitalCardDTO mapToHospitalCardDTO(Hospital hospital) {
        Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                hospital.getMorning_peak(),
                hospital.getAfternoon_peak(),
                hospital.getNight_peak()
        );

        HospitalCardDTO cardDTO = hospitalHelperMapper.hospitalToHospitalCardDTO(hospital);
        cardDTO.setConcurrencyProfile(concurrencyProfile);
        return cardDTO;
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
        // Mapeo de DTO a Hospital
        Hospital hospital = hospitalHelperMapper.toHospital(dto);

        // Guardar el hospital en la base de datos
        Hospital savedHospital = hospitalRepository.save(hospital);

        // Crear la lista de HospitalEps
        List<HospitalEps> hospitalEpsList = dto.getEps_id().stream()
                .map(eps -> {
                    Eps epsEntity = epsRepository.findById(eps.getId())
                            .orElseThrow(() -> new RuntimeException("EPS not found with ID: " + eps.getId()));
                    return new HospitalEps(new HospitalEpsId(savedHospital.getId(), eps.getId()), savedHospital, epsEntity);
                })
                .collect(Collectors.toList());

        // Guardar la lista de HospitalEps
        hospitalEpsRepository.saveAll(hospitalEpsList);

        return savedHospital;
    }

    @Override
    public Hospital update(Long id, HospitalCreateResponseDTO dto) {
        // Buscar el hospital existente
        Hospital existingHospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital with id " + id + " not found"));

        // Usar el mapeador para actualizar los atributos del hospital existente
        hospitalHelperMapper.hospitalCreateResponseDTOtoHospital(dto, existingHospital);

        // Guardar el hospital actualizado en la base de datos
        Hospital updatedHospital = hospitalRepository.save(existingHospital);

        // Manejo de HospitalEps
        Set<HospitalEps> existingHospitalEps = new HashSet<>(hospitalEpsRepository.findAllByHospitalId(id));

        Set<HospitalEps> newHospitalEps = dto.getEps_id().stream()
                .map(eps -> {
                    Eps epsEntity = epsRepository.findById(eps.getId())
                            .orElseThrow(() -> new RuntimeException("EPS not found with ID: " + eps.getId()));
                    return new HospitalEps(new HospitalEpsId(id, eps.getId()), updatedHospital, epsEntity);
                })
                .collect(Collectors.toSet());

        // Eliminar las EPS que ya no están asociadas
        existingHospitalEps.removeAll(newHospitalEps);
        hospitalEpsRepository.deleteAll(existingHospitalEps);

        // Guardar las nuevas EPS
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
        // Buscar el hospital existente
        Hospital hospitalExists = hospitalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hospital with id " + id + " not found"));

        Towns townExist = hospitalExists.getTown_id();
        TownsDTO town = TownsDTO.builder()
                .name(townExist.getName())
                .build();

        // Obtener la lista de EPS y convertirla a una lista de nombres
        List<String> epsName = hospitalExists.getEps_id().stream()
                .map(hospitalEps -> hospitalEps.getEps().getName())
                .collect(Collectors.toList());

        // Generar el perfil de concurrencia
        Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                hospitalExists.getMorning_peak(),
                hospitalExists.getAfternoon_peak(),
                hospitalExists.getNight_peak()
        );

        // Mapear el Hospital a HospitalGetResponseDTO
        HospitalGetResponseDTO hospitalGetResponseDTO = hospitalMapper.toHospitalGetResponseDTO(hospitalExists);

        // Establecer los EPS y el perfil de concurrencia
        hospitalGetResponseDTO.setEps_id(epsName);
        hospitalGetResponseDTO.setConcurrencyProfile(concurrencyProfile);
        hospitalGetResponseDTO.setTown_id(town);

        return hospitalGetResponseDTO;
    }

    @Override
    public List<HospitalGetResponseDTO> readALl() {
        List<Hospital> hospitalExists = this.hospitalRepository.findAll();

        // Usar el mapeador para convertir a DTOs
        return hospitalExists.stream()
                .map(hospital -> {
                    Towns townExist = hospital.getTown_id();
                    TownsDTO townsDTO = TownsDTO.builder()
                            .name(townExist.getName())
                            .build();

                    // Mapear el hospital a DTO y agregar el townDTO
                    Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                            hospital.getMorning_peak(),
                            hospital.getAfternoon_peak(),
                            hospital.getNight_peak()
                    );

                    HospitalGetResponseDTO hospitalGetResponseDTO = hospitalMapper.toHospitalGetResponseDTO(hospital);
                    hospitalGetResponseDTO.setTown_id(townsDTO);
                    hospitalGetResponseDTO.setConcurrencyProfile(concurrencyProfile);

                    return hospitalGetResponseDTO;
                })
                .collect(Collectors.toList());
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