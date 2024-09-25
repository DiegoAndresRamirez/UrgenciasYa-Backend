package com.urgenciasYa.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/*
 * Data Transfer Object (DTO) for login requests.
 * This class is used to capture the login credentials provided by the user.
 */

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Nombre es requerido") // Validation annotation to ensure the email field is not empty
    private String email; // The email address of the user

    @NotBlank(message = "Contraseña es requerida") // Validation annotation to ensure the password field is not empty
    private String password; // The password for the user account
}
