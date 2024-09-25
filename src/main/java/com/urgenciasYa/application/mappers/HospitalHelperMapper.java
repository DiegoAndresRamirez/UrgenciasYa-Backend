package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.response.HospitalCardDTO;
import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.domain.model.Hospital;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component // Marks this class as a Spring component for dependency injection
public class HospitalHelperMapper {

    // Converts a Hospital entity to a HospitalCardDTO, including EPS names and town details
    public HospitalCardDTO hospitalToHospitalCardDTO(Hospital hospital) {
        return HospitalCardDTO.builder()
                .id(hospital.getId()) // Sets hospital ID
                .url_image(hospital.getUrl_image()) // Sets hospital image URL
                .phone_number(hospital.getPhone_number()) // Sets hospital phone number
                .name(hospital.getName()) // Sets hospital name
                .rating(hospital.getRating()) // Sets hospital rating
                .howtogetthere(hospital.getHowtogetthere()) // Sets instructions on how to reach the hospital
                .nameTown(hospital.getTown_id().getName()) // Maps the town name from the town entity
                .nameEps(hospital.getEps_id().stream()
                        .map(hospitalEps -> hospitalEps.getEps().getName()) // Extracts and joins EPS names
                        .collect(Collectors.joining(", ")))
                .latitude(hospital.getLatitude()) // Sets the hospital's latitude
                .longitude(hospital.getLongitude()) // Sets the hospital's longitude
                .build();
    }

    // Converts a HospitalCreateResponseDTO to a Hospital entity
    public Hospital toHospital(HospitalCreateResponseDTO dto) {
        return Hospital.builder()
                .url_image(dto.getUrl_image()) // Sets the hospital image URL
                .phone_number(dto.getPhone_number()) // Sets the hospital phone number
                .name(dto.getName()) // Sets the hospital name
                .rating(dto.getRating()) // Sets the hospital rating
                .morning_peak(dto.getMorning_peak()) // Sets peak time in the morning
                .afternoon_peak(dto.getAfternoon_peak()) // Sets peak time in the afternoon
                .night_peak(dto.getNight_peak()) // Sets peak time at night
                .howtogetthere(dto.getHowtogetthere()) // Sets directions on how to get to the hospital
                .town_id(dto.getTown_id()) // Sets the hospital's town
                .latitude(dto.getLatitude()) // Sets the hospital's latitude
                .longitude(dto.getLongitude()) // Sets the hospital's longitude
                .build();
    }

    // Updates an existing Hospital entity with values from a HospitalCreateResponseDTO
    public void hospitalCreateResponseDTOtoHospital(HospitalCreateResponseDTO dto, Hospital existingHospital) {
        existingHospital.setUrl_image(dto.getUrl_image()); // Updates the hospital image URL
        existingHospital.setPhone_number(dto.getPhone_number()); // Updates the hospital phone number
        existingHospital.setName(dto.getName()); // Updates the hospital name
        existingHospital.setRating(dto.getRating()); // Updates the hospital rating
        existingHospital.setMorning_peak(dto.getMorning_peak()); // Updates morning peak time
        existingHospital.setAfternoon_peak(dto.getAfternoon_peak()); // Updates afternoon peak time
        existingHospital.setNight_peak(dto.getNight_peak()); // Updates night peak time
        existingHospital.setHowtogetthere(dto.getHowtogetthere()); // Updates directions to the hospital
        existingHospital.setTown_id(dto.getTown_id()); // Updates the town
        existingHospital.setLatitude(dto.getLatitude()); // Updates latitude
        existingHospital.setLongitude(dto.getLongitude()); // Updates longitude
    }
}
