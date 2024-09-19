package com.urgenciasYa.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Nombre es requerido")
    private String name;

    @NotBlank(message = "Contraseña es requerida")
    private String password;
}
