package com.urgenciasYa.application.dto.response;

import lombok.*;

/*
 * Data Transfer Object (DTO) representing a user role response.
 * This DTO contains the details of a user's role.
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponseDTO {
    private String code;  // Code representing the user's role
}
