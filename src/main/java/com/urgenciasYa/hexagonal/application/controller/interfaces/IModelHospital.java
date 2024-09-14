package com.urgenciasYa.hexagonal.application.controller.interfaces;

import com.urgenciasYa.hexagonal.application.controller.generic.*;
import com.urgenciasYa.hexagonal.application.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.hexagonal.domain.model.Hospital;

public interface IModelHospital extends Create<HospitalCreateResponseDTO>, Update<HospitalCreateResponseDTO, Long>, Delete<Long>, GetById<Hospital, Long>, GetAll<Hospital> {
}
