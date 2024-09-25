package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.response.EpsShiftResponseDTO;
import com.urgenciasYa.application.dto.response.HospitalShiftResponseDTO;
import com.urgenciasYa.application.dto.response.UserShiftResponseDTO;
import com.urgenciasYa.domain.model.Eps;
import com.urgenciasYa.domain.model.Hospital;
import com.urgenciasYa.domain.model.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
    UserShiftResponseDTO shiftToUserShiftResponseDTO(Shift shift);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    HospitalShiftResponseDTO hospitalToHospitalShiftResponseDTO(Hospital hospital);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    EpsShiftResponseDTO epsToEpsShiftResponseDTO(Eps eps);


}
