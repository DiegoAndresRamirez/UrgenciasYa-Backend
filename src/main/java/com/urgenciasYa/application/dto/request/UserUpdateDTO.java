package com.urgenciasYa.application.dto.request;

import com.urgenciasYa.application.dto.response.EpsResponseDTO;
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
public class UserUpdateDTO {
    @NotBlank(message = "Nombre es Requerido")
    private String name;

    @Email(message = "Email es requerido")
    private String email;

    @NotNull(message = "EPS es requerida")
    @Valid
    private EpsResponseDTO eps;

    @NotBlank(message = "Documento requerido")
    private String document;
}