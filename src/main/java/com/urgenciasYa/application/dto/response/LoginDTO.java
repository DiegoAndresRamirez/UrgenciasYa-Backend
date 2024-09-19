package com.urgenciasYa.application.dto.response;

import com.urgenciasYa.domain.model.RoleEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class LoginDTO {
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
    @NotBlank(message = "token es requerida")
    private String token;

    private RoleEntity role;
}
