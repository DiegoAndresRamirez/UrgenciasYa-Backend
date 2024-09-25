package com.urgenciasYa.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/*
 * Data Transfer Object (DTO) representing a town (city).
 * This DTO contains the details of a town.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representation of a city")
public class TownsDTO {
    @Schema(description = "City name",example = "envigado")
    private String name;
}
