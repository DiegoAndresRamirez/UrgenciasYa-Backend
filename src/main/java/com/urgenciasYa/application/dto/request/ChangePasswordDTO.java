package com.urgenciasYa.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/*
 * Data Transfer Object (DTO) for changing a user's password.
 * This class contains the current password, new password, and confirmation of the new password.
 */

@Getter
@Setter
@Builder
public class ChangePasswordDTO {
    @NotBlank(message = "La contraseña actual no puede estar vacía")  // Validation constraint ensuring current password is not blank
    private String currentPassword; // Field to store the current password

    @NotBlank(message = "La nueva contraseña no puede estar vacía") // Validation constraint ensuring new password is not blank
    @Size(min = 8, message = "La nueva contraseña debe tener al menos 8 caracteres") // Validation ensuring new password has a minimum length of 8
    private String newPassword; // Field to store the new password

    @NotBlank(message = "La confirmación de la nueva contraseña no puede estar vacía") // Validation constraint ensuring confirmation password is not blank
    private String confirmNewPassword; // Field to store confirmation of the new password
}

