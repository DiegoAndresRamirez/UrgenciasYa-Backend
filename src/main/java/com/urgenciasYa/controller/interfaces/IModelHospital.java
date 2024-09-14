package com.urgenciasYa.controller.interfaces;

import com.urgenciasYa.controller.generic.Create;
import com.urgenciasYa.controller.generic.Delete;
import com.urgenciasYa.controller.generic.GetById;
import com.urgenciasYa.controller.generic.Update;
import com.urgenciasYa.dto.request.HospitalCreateResponseDTO;
import com.urgenciasYa.model.Hospital;

public interface IModelHospital extends Create<HospitalCreateResponseDTO> , Update<HospitalCreateResponseDTO, Long> , Delete<Long>, GetById<Hospital, Long> {
}
