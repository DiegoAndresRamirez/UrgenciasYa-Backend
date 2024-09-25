package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.response.HospitalCardDTO;
import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.domain.model.Hospital;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HospitalHelperMapper {

    public HospitalCardDTO hospitalToHospitalCardDTO(Hospital hospital){
        return HospitalCardDTO.builder()
                .id(hospital.getId())
                .url_image(hospital.getUrl_image())
                .phone_number(hospital.getPhone_number())
                .name(hospital.getName())
                .rating(hospital.getRating())
                .howtogetthere(hospital.getHowtogetthere())
                .nameTown(hospital.getTown_id().getName())
                .nameEps(hospital.getEps_id().stream().map(hospitalEps -> hospitalEps.getEps().getName()).collect(Collectors.joining(", ")))
                .latitude(hospital.getLatitude())
                .longitude(hospital.getLongitude())
                .build();
    };

    public Hospital toHospital(HospitalCreateResponseDTO dto){
            return Hospital.builder()
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
    }

    public void hospitalCreateResponseDTOtoHospital(HospitalCreateResponseDTO dto, Hospital existingHospital){
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
    }
}
