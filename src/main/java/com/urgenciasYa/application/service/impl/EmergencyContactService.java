package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.service.IModel.IEmergencyContactModel;
import com.urgenciasYa.domain.model.EmergencyEntity;
import com.urgenciasYa.infrastructure.persistence.EmergencyContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmergencyContactService implements IEmergencyContactModel {

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Override
    public EmergencyEntity create(EmergencyContactRequestDTO entity) {
        EmergencyEntity emergencyEntity = new EmergencyEntity();
        emergencyEntity.builder().name(entity.getName()).phone(entity.getPhone()).build();
        return emergencyContactRepository.save(emergencyEntity);
    }
}
