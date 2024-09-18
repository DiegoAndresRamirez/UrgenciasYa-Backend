package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import com.urgenciasYa.application.service.IModel.IEmergencyContactModel;
import com.urgenciasYa.domain.model.EmergencyEntity;
import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.persistence.EmergencyContactRepository;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmergencyContactService implements IEmergencyContactModel {

    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public EmergencyEntity create(Long userId, String name, String phone) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + userId));

        if (user.getEmergency() != null) {
            throw new IllegalStateException("El usuario ya tiene un contacto de emergencia");
        }

        EmergencyEntity emergency = EmergencyEntity.builder()
                .name(name.trim())
                .phone(phone.trim())
                .user(user)
                .build();

        emergency = emergencyContactRepository.save(emergency);

        user.setEmergency(emergency);
        userRepository.save(user);

        return emergency;
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
