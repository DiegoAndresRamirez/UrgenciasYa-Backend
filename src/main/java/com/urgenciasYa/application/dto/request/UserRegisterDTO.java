package com.urgenciasYa.application.dto.request;

import com.urgenciasYa.application.dto.response.EmergencyContactResponseDTO;
import com.urgenciasYa.application.dto.response.EpsUserResponseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * Data Transfer Object (DTO) for registering a new user.
 * This class captures the necessary details to create a new user in the system.
 */

@Builder
@Getter
@Setter
public class UserRegisterDTO {

    @NotBlank(message = "Nombre es Requerido") // Ensures the 'name' field is not empty or null
    private String name;  // User's name

    @Email(message = "Email es requerido") // Validates that the 'email' field contains a valid email address
    private String email; // User's email

    @NotBlank(message = "EPS es requerida") // Ensures the 'eps' field is not empty or null
    private EpsUserResponseDTO eps; // User's EPS (Healthcare Provider) information, represented by a DTO

    @NotBlank(message = "Contraseña es requerida") // Ensures the 'password' field is not empty or null
    private String password; // User's password

    private EmergencyContactResponseDTO contact; // Optional field: User's emergency contact, can be null

    @NotBlank(message = "Documento requerido") // Ensures the 'document' field is not empty or null
    private String document; // User's identification document
}
