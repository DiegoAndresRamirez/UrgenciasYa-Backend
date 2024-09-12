package com.urgenciasYa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TownCreateDTO {
    @NotBlank
    private String name;
}
