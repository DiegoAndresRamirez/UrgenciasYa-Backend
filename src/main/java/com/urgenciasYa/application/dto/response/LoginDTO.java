package com.urgenciasYa.application.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class LoginDTO {
    @NotBlank(message = "Id requerido")
    private Long id;
    @NotBlank(message = "token es requerida")
    private String token;
}
