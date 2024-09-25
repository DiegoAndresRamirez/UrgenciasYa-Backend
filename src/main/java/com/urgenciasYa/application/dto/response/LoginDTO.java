package com.urgenciasYa.application.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * Data Transfer Object (DTO) for login responses.
 * This DTO contains information returned upon successful login,
 * including user ID, role ID, and authentication token.
 */

@Builder
@Setter
@Getter
public class LoginDTO {
    @NotBlank(message = "Id requerido") // Validation to ensure ID is provided
    private Long id;
    @NotBlank(message = "Id requerido")
    private Long role_id;
    @NotBlank(message = "token es requerida")
    private String token; // Authentication token for the session
}
