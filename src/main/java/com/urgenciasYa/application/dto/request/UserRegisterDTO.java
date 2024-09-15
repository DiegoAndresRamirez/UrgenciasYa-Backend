package com.urgenciasYa.application.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String eps;
    @NotBlank(message = "Contraseña es requerida")
    private String password;
    @NotBlank(message = "Documento requerido")
    private String document;
}
