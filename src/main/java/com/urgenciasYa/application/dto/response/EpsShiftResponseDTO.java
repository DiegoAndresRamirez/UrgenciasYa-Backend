package com.urgenciasYa.application.dto.response;

import lombok.*;

/*
 * Data Transfer Object (DTO) for representing an EPS shift.
 * It includes basic information such as the shift ID and name.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpsShiftResponseDTO {
    private Integer id; // The unique identifier for the EPS shift
    private String name; // The name associated with the EPS shift
}
