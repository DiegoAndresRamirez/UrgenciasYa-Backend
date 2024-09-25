package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.response.HospitalCardDTO;
import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.application.dto.response.HospitalGetResponseDTO;
import com.urgenciasYa.domain.model.Hospital;
import com.urgenciasYa.domain.model.HospitalEps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring") // Defines the mapper as a Spring component for dependency injection
public interface HospitalMapper {

    // Converts a Hospital entity to its corresponding DTO for card display
    HospitalCardDTO hospitalToHospitalCardDTO(Hospital hospital);

    // Maps a Hospital entity to a detailed DTO, including relations with EPS and town
    @Mapping(target = "town_id", source = "town_id")
    @Mapping(target = "eps_id", expression = "java(mapEpsNames(hospital.getEps_id()))")
    HospitalGetResponseDTO toHospitalGetResponseDTO(Hospital hospital);

    // Helper method to map a list of HospitalEps to a list of EPS names
    default List<String> mapEpsNames(List<HospitalEps> hospitalEpsList) {
        return hospitalEpsList.stream()
                .map(hospitalEps -> hospitalEps.getEps().getName()) // Extracts the EPS name from each HospitalEps entity
                .collect(Collectors.toList()); // Collects the results into a list of strings
    }
}
