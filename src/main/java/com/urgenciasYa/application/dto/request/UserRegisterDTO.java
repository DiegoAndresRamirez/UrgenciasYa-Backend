package com.urgenciasYa.application.dto.request;

import com.urgenciasYa.application.dto.response.EmergencyContactResponseDTO;
import com.urgenciasYa.application.dto.response.EpsUserResponseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRegisterDTO {

    @NotBlank(message = "Nombre es Requerido")
    private String name;

    @Email(message = "Email es requerido")
    private String email;

    @NotBlank(message = "EPS es requerida")
    private EpsUserResponseDTO eps;

    @NotBlank(message = "Contraseña es requerida")
    private String password;

    private EmergencyContactResponseDTO contact; // Ahora puede ser null

    @NotBlank(message = "Documento requerido")
    private String document;
}
