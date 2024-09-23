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

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HospitalService implements IHospitalModel {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalEpsRepository hospitalEpsRepository;

    @Autowired
    private TownsRepository townsRepository;

    @Autowired
    private EpsRepository epsRepository;

    private static final double EARTH_RADIUS = 6371;

    public List<HospitalCardDTO> getHospitalsNearby(HospitalSearchRequestDTO requestDTO) {
        String epsName = requestDTO.getEps();
        String townName = requestDTO.getTown();
        BigDecimal userLatitude = requestDTO.getLatitude(); // Cambiado a Double para poder ser null
        BigDecimal userLongitude = requestDTO.getLongitude(); // Cambiado a Double para poder ser null

        // Encuentra los hospitales basados en EPS y town
        List<Hospital> hospitals = hospitalRepository.findByEpsNameAndTown(epsName, townName);

        if (userLatitude != null && userLongitude != null) {
            // Si latitud y longitud están disponibles, calcula la distancia
            hospitals.sort((h1, h2) -> {
                BigDecimal distanceToH1 = calculateDistance(
                        userLatitude,
                        userLongitude,
                        h1.getLatitude(),
                        h1.getLongitude()
                );
                BigDecimal distanceToH2 = calculateDistance(
                        userLatitude,
                        userLongitude,
                        h2.getLatitude(),
                        h2.getLongitude()
                );
                return distanceToH1.compareTo(distanceToH2);
            });
        }

        return hospitals.stream().map(hospital -> {
            Map<String, Integer> concurrencyProfile = ConcurrencyAlgorithm.generateConcurrencyProfile(
                    hospital.getMorning_peak(),
                    hospital.getAfternoon_peak(),
                    hospital.getNight_peak()
            );

            return HospitalCardDTO.builder()
                    .id(hospital.getId())
                    .url_image(hospital.getUrl_image())
                    .phone_number(hospital.getPhone_number())
                    .name(hospital.getName())
                    .rating(hospital.getRating())
                    .howtogetthere(hospital.getHowtogetthere())
                    .nameTown(hospital.getTown_id().getName())
                    .nameEps(hospital.getEps_id().stream().map(hospitalEps -> hospitalEps.getEps().getName()).collect(Collectors.joining(", ")))
                    .concurrencyProfile(concurrencyProfile)
                    .build();
        }).collect(Collectors.toList());
    }

    private BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        // Convertimos BigDecimal a double para el cálculo
        double dLat = Math.toRadians(lat2.doubleValue() - lat1.doubleValue());
        double dLon = Math.toRadians(lon2.doubleValue() - lon1.doubleValue());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1.doubleValue())) * Math.cos(Math.toRadians(lat2.doubleValue())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculamos la distancia y convertimos el resultado de nuevo a BigDecimal
        return BigDecimal.valueOf(EARTH_RADIUS * c);
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
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

        Hospital savedHospital = hospitalRepository.save(hospital);

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
    public Hospital update(Long id, HospitalCreateResponseDTO dto) {
        Hospital existingHospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital with id " + id + " not found"));

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

        Hospital updatedHospital = hospitalRepository.save(existingHospital);

        Set<HospitalEps> existingHospitalEps = new HashSet<>(hospitalEpsRepository.findAllByHospitalId(id));
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

    public Map<String, Integer> getConcurrencyProfileByHospital(Long hospitalId, String eps, String town, BigDecimal latitude, BigDecimal longitude) {
        HospitalSearchRequestDTO requestDTO = new HospitalSearchRequestDTO(eps, town, latitude, longitude);
        List<HospitalCardDTO> hospitals = getHospitalsNearby(requestDTO);

        return hospitals.stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .map(HospitalCardDTO::getConcurrencyProfile)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Hospital not found or has no concurrency profile"));
    }
}