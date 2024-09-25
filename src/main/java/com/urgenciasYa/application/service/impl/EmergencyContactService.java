package com.urgenciasYa.application.service.impl;


import com.urgenciasYa.application.service.IModel.IEmergencyContactModel;
import com.urgenciasYa.domain.model.EmergencyEntity;
import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.persistence.EmergencyContactRepository;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * Service implementation for managing emergency contacts.
 * This service implements the IEmergencyContactModel interface.
 */

@Service
public class EmergencyContactService implements IEmergencyContactModel {

    @Autowired
    private EmergencyContactRepository emergencyContactRepository; // Repository for emergency contacts

    @Autowired
    private UserRepository userRepository; // Repository for users

    //Creates a new emergency contact for a user.
    @Transactional
    public EmergencyEntity create(Long userId, String name, String phone) {
        // Validate input parameters
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }

        // Find the user by ID
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + userId));

        // Check if the user already has an emergency contact
        if (user.getEmergency() != null) {
            throw new IllegalStateException("El usuario ya tiene un contacto de emergencia");
        }

        // Create the emergency contact entity
        EmergencyEntity emergency = EmergencyEntity.builder()
                .name(name.trim()) // Trim whitespace from name
                .phone(phone.trim())  // Trim whitespace from phone
                .user(user) // Associate the user with the emergency contact
                .build();

        // Save the emergency contact to the repository
        emergency = emergencyContactRepository.save(emergency);

        // Link the emergency contact to the user
        user.setEmergency(emergency);
        userRepository.save(user);

        return emergency; // Return the created emergency contact
    }

    /**
     * Updates an existing emergency contact.
     */

    @Override
    public EmergencyEntity update(Long id, String name, String phone) {
        // Validate input parameters
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }

        // Find the emergency contact by ID
        EmergencyEntity emergencyEntity = emergencyContactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emergency contact not found"));

        // Update the emergency contact's name and phone
        emergencyEntity.setName(name.trim());
        emergencyEntity.setPhone(phone.trim());

        // Save the updated emergency contact to the repository
        return emergencyContactRepository.save(emergencyEntity);
    }

}
