package com.urgenciasYa.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representation of a Eps")
public class EpsUserResponseDTO {
    @Schema(description = "Eps Id", example = "1")
    private Integer id;
    @Schema(description = "Eps name",example = "nueva eps")
    private String name;
}
