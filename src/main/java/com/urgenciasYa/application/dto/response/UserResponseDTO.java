package com.urgenciasYa.application.dto.response;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String eps;
    private String email;
    private String document;
    private EmergencyContactRequestDTO emergency;
    private RoleResponseDTO role;
}
