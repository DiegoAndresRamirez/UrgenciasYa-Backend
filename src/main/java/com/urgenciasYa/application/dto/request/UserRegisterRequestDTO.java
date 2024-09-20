package com.urgenciasYa.application.dto.request;

import com.urgenciasYa.application.dto.response.EpsResponseDTO;
import com.urgenciasYa.application.dto.response.EpsUserResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserRegisterRequestDTO {
    @NotBlank(message = "Nombre es Requerido")
    private String name;

    @Email(message = "Email es requerido")
    private String email;

    @NotNull(message = "EPS es requerida")
    @Valid // Esto asegura que se valide el contenido del objeto eps
    private EpsResponseDTO eps;

    @NotBlank(message = "Contraseña es requerida")
    private String password;

    @NotBlank(message = "Documento requerido")
    private String document;
}
