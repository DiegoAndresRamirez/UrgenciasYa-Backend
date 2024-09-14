package com.urgenciasYa.service.IModel;

import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.service.crud.CreateDTO;

public interface IHospitalModel extends CreateDTO<HospitalCreateResponseDTO, Hospital> {
}
