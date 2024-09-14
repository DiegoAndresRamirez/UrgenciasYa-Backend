package com.urgenciasYa.hexagonal.application.service.IModel;

import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.hexagonal.application.service.crud.*;
import com.urgenciasYa.hexagonal.domain.model.Hospital;

public interface IHospitalModel extends CreateDTO<HospitalCreateResponseDTO, Hospital>, Update<Long, HospitalCreateResponseDTO, Hospital>, Delete<Long>, ReadById<Hospital, Long>, ReadAll<Hospital> {
}
