package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.application.service.crud.*;
import com.urgenciasYa.hexagonal.application.service.crud.*;
import com.urgenciasYa.domain.model.Hospital;

public interface IHospitalModel extends CreateDTO<HospitalCreateResponseDTO, Hospital>, Update<Long, HospitalCreateResponseDTO, Hospital>, Delete<Long>, ReadById<Hospital, Long>, ReadAll<Hospital> {
}
