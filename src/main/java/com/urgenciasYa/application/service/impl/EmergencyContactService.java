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
        EmergencyEntity emergencyEntity = EmergencyEntity.builder().name(entity.getName()).phone(entity.getPhone()).build();
        return emergencyContactRepository.save(emergencyEntity);
    }


    @Override
    public EmergencyEntity Update(Long id, EmergencyContactRequestDTO emergencyContactRequestDTO) {
        EmergencyEntity emergencyEntity = emergencyContactRepository.findById(id).orElseThrow(()-> new RuntimeException("Emergency contact not found"));

        EmergencyEntity emergency = EmergencyEntity.builder().id(emergencyEntity.getId()).name(emergencyContactRequestDTO.getName()).phone(emergencyContactRequestDTO.getPhone()).build();

        return emergencyContactRepository.save(emergency);
    }

    @Override
    public void delete(Long id) {
        EmergencyEntity emergencyEntity = emergencyContactRepository.findById(id).orElseThrow(()-> new RuntimeException("Emergency contact not found"));
        emergencyContactRepository.deleteById(id);
    }
}
