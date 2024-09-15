package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.response.HospitalCreateResponseDTO;
import com.urgenciasYa.application.service.crud.*;
import com.urgenciasYa.domain.model.Hospital;

public interface IHospitalModel extends CreateDTO<HospitalCreateResponseDTO, Hospital>, Delete<Long>, ReadById<Hospital, Long>, ReadAll<Hospital> {
    Hospital update (Long id, HospitalCreateResponseDTO entity);

}
