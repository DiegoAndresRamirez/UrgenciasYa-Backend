package com.urgenciasYa.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Email(message = "El correo electrónico debe tener un formato válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String newPassword;

    private String currentPassword;

    private String phone;
    private String emergencyContact;
    private String eps; // EPS actualizada

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String name, String email, String newPassword, String currentPassword, String phone, String emergencyContact, String eps) {
        this.name = name;
        this.email = email;
        this.newPassword = newPassword;
        this.currentPassword = currentPassword;
        this.phone = phone;
        this.emergencyContact = emergencyContact;
        this.eps = eps;
    }

}
