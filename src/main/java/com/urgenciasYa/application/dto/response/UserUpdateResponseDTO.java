package com.urgenciasYa.application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String emergencyContact;
    private String eps; // EPS actualizada
}
