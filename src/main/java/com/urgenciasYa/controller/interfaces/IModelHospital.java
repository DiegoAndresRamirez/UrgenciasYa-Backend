package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.controller.generic.Create;
import com.urgenciasYa.controller.generic.Update;
import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;

public interface IModelHospital extends Create<HospitalCreateResponseDTO> , Update<HospitalCreateResponseDTO, Long> {
}
