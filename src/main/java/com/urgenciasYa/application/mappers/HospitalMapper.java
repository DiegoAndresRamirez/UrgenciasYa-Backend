package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.response.HospitalCardDTO;
import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.application.dto.response.HospitalGetResponseDTO;
import com.urgenciasYa.domain.model.Hospital;
import com.urgenciasYa.domain.model.HospitalEps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HospitalMapper {

    HospitalCardDTO hospitalToHospitalCardDTO(Hospital hospital);

    List<HospitalCardDTO> hospitalsToHospitalCardDTOs(List<Hospital> hospitals);

    @Mapping(target = "town_id", source = "town_id")
    @Mapping(target = "eps_id", expression = "java(mapEpsNames(hospital.getEps_id()))")
    HospitalGetResponseDTO toHospitalGetResponseDTO(Hospital hospital);


    default List<String> mapEpsNames(List<HospitalEps> hospitalEpsList) {
        return hospitalEpsList.stream()
                .map(hospitalEps -> hospitalEps.getEps().getName())
                .collect(Collectors.toList());
    }



}
