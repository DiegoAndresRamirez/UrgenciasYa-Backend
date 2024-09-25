package com.urgenciasYa.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/*
 * Data Transfer Object (DTO) representing a user's EPS information.
 * This includes the EPS ID and name, useful for associating a user with an EPS.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representation of a Eps")
public class EpsUserResponseDTO {
    @Schema(description = "Eps Id", example = "1") // Swagger schema for EPS ID with an example
    private Integer id;  // The unique identifier for the EPS
    @Schema(description = "Eps name",example = "nueva eps") // Swagger schema for EPS name with an example
    private String name;  // The name of the EPS
}
