package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.response.UserShiftResponseDTO;
import com.urgenciasYa.domain.model.Shift;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShiftMapper {
    UserShiftResponseDTO shiftToUserShiftResponseDTO(Shift shift);
}
