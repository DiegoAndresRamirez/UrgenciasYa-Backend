package com.urgenciasYa.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Representation of a Eps")
public class EpsResponseDTO {
    @Schema(description = "Eps name",example = "nueva eps")
    private String name;
}
