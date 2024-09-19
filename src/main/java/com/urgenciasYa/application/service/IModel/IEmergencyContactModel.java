package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.service.crud.CreateDTO;
import com.urgenciasYa.application.service.crud.Delete;
import com.urgenciasYa.application.service.crud.Update;
import com.urgenciasYa.domain.model.EmergencyEntity;

public interface IEmergencyContactModel {
    EmergencyEntity update(Long id, String name, String phone);
    EmergencyEntity create (Long userId, String name, String phone);
}
