package com.urgenciasYa.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representation of a Eps")
public class EpsResponseDTO {
    @NotBlank(message = "El nombre de la EPS es requerido") // Añadido para la validación
    @Schema(description = "Eps name", example = "nueva eps")
    private String name;
}
