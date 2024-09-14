package com.urgenciasYa.service.IModel;

import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.model.Hospital;
import com.urgenciasYa.service.crud.CreateDTO;
import com.urgenciasYa.service.crud.Update;

public interface IHospitalModel extends CreateDTO<HospitalCreateResponseDTO, Hospital> , Update<Long, HospitalCreateResponseDTO, Hospital> {
}
