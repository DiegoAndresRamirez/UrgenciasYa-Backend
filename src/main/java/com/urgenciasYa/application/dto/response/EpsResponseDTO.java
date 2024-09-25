package com.urgenciasYa.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/*
 * Data Transfer Object (DTO) for representing an EPS (Health Provider).
 * It includes validation and OpenAPI documentation annotations.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representation of a Eps") // OpenAPI schema description
public class EpsResponseDTO {
    @NotBlank(message = "El nombre de la EPS es requerido") // Added for the validation
    @Schema(description = "Eps name", example = "nueva eps") // OpenAPI annotation for documenting the field
    private String name; // The name of the EPS (Health Provider)
}
