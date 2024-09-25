package com.urgenciasYa.application.dto.request;

import com.urgenciasYa.application.dto.response.EpsResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * Data Transfer Object (DTO) for user update requests.
 * This class captures the data needed to update an existing user's information in the system.
 */

@Builder
@Getter
@Setter
public class UserUpdateDTO {
    @NotBlank(message = "Nombre es Requerido") // Ensures the 'name' field is not empty or null
    private String name; // User's updated name

    @Email(message = "Email es requerido") // Validates that the 'email' field contains a valid email address
    private String email; // User's updated email

    @NotNull(message = "EPS es requerida") // Ensures the 'eps' field is not null
    @Valid  // Ensures that the EPS object is also validated according to its constraints
    private EpsResponseDTO eps; // Updated EPS (Healthcare Provider) information, represented by a DTO

    @NotBlank(message = "Documento requerido") // Ensures the 'document' field is not empty or null
    private String document; // User's updated identification document
}