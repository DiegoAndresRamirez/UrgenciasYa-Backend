package com.urgenciasYa.application.service.IModel;

import com.urgenciasYa.domain.model.EmergencyEntity;

/*
 * Interface for managing emergency contacts.
 */

public interface IEmergencyContactModel {

    //  Updates an existing emergency contact.
    EmergencyEntity update(Long id, String name, String phone);

    //Creates a new emergency contact associated with a user.
    EmergencyEntity create (Long userId, String name, String phone);
}
