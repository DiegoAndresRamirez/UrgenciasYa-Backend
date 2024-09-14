package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.controller.generic.*;
import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.hexagonal.domain.model.Hospital;

public interface IModelHospital extends Create<HospitalCreateResponseDTO> , Update<HospitalCreateResponseDTO, Long> , Delete<Long>, GetById<Hospital, Long> , GetAll<Hospital> {
}
