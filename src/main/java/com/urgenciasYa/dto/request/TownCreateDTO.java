package com.urgenciasYa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TownCreateDTO {
    @NotBlank
    private String name;
}
