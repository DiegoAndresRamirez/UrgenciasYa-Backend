package com.urgenciasYa.service.IModel;

import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.hexagonal.domain.model.Hospital;
import com.urgenciasYa.service.crud.*;

public interface IHospitalModel extends CreateDTO<HospitalCreateResponseDTO, Hospital> , Update<Long, HospitalCreateResponseDTO, Hospital> , Delete<Long> , ReadById<Hospital, Long>, ReadAll<Hospital> {
}
