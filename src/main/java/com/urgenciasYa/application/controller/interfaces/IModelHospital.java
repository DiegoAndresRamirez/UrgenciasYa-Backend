package com.urgenciasYa.application.controller.interfaces;

import com.urgenciasYa.application.controller.generic.*;
import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.domain.model.Hospital;

public interface IModelHospital extends Create<HospitalCreateResponseDTO>, Update<HospitalCreateResponseDTO, Long>, Delete<Long>, GetById<Hospital, Long>, GetAll<Hospital> {
}
