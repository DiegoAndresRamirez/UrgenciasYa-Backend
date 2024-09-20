package com.urgenciasYa.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserShiftResponseDTO {
    private Long id;
    private String shiftNumber;
    private LocalDateTime estimatedTime;
    private String status;
    private UserResponseDTO user; // Solo el usuario simplificado
    private Long hospitalId; // Solo ID del hospital
    private Integer epsId; // Solo ID de la EPS
}
