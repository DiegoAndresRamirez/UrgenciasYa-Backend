package com.urgenciasYa.application.mappers;

import com.urgenciasYa.application.dto.request.TownCreateDTO;
import com.urgenciasYa.domain.model.Towns;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TownMapper {

   // TownMapper INSTANCE = Mappers.getMapper(TownMapper.class);

    Towns createDTOToTown(TownCreateDTO townCreateDTO);

}
