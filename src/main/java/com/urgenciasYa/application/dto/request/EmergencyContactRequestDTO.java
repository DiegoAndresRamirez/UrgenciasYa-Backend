package com.urgenciasYa.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data Transfer Object (DTO) for creating or updating an emergency contact.
 * This class contains the name and phone number of the emergency contact.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmergencyContactRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")  // Validation constraint ensuring name is not blank
    private String name; // Field to store the name of the emergency contact

    @NotBlank(message = "El teléfono no puede estar vacío") // Validation constraint ensuring phone number is not blank
    private String phone; // Field to store the phone number of the emergency contact
}