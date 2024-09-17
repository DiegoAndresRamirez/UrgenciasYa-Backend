package com.urgenciasYa.application.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmergencyContactRequestDTO {
    private String name;
    private String phone;

    public EmergencyContactRequestDTO(String emergencyContact) {
    }
}
