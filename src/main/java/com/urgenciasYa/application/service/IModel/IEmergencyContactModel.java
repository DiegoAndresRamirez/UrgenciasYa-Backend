package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.service.crud.CreateDTO;
import com.urgenciasYa.application.service.crud.Update;
import com.urgenciasYa.domain.model.EmergencyEntity;

public interface IEmergencyContactModel extends CreateDTO<EmergencyContactRequestDTO, EmergencyEntity>{
    EmergencyEntity Update(Long id, EmergencyContactRequestDTO emergencyContactRequestDTO);
}
