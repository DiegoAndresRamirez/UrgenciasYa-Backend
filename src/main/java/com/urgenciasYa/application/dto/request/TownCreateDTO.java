package com.urgenciasYa.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/*
 * Data Transfer Object (DTO) for creating a new Town.
 * This class is used to capture the necessary information to create a new town.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TownCreateDTO {
    @NotBlank // Ensures the name field is not empty or null
    private String name; // The name of the town to be created
}
