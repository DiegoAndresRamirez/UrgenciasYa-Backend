package com.urgenciasYa.application.dto.response;

import com.urgenciasYa.application.dto.request.EmergencyContactRequestDTO;
import lombok.*;

/*
 * Data Transfer Object (DTO) representing a user.
 * This DTO contains user-related information including emergency contact and role.
 */

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
    private EmergencyContactRequestDTO emergency; // Emergency contact details
    private RoleResponseDTO role; // User's role information
}
