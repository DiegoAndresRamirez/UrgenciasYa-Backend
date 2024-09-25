package com.urgenciasYa.application.dto.response;

import lombok.*;

/*
 * Data Transfer Object (DTO) representing a hospital shift.
 * This DTO contains basic information about a hospital shift,
 * including its identifier and name.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalShiftResponseDTO {
    private Long id;  // Unique identifier for the hospital shift
    private String name; // Name of the hospital shift (e.g., "Morning", "Evening")
}
