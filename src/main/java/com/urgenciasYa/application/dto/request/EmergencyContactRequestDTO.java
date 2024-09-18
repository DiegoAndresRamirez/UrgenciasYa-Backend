package com.urgenciasYa.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmergencyContactRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String phone;
}