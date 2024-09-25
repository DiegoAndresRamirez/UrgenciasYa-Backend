package com.urgenciasYa.application.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/*
 * Data Transfer Object (DTO) representing the user's shift details.
 * This DTO contains information about the shift assigned to a user.
 */

@Data
@Builder
public class UserShiftResponseDTO {
    private Long id;
    private String shiftNumber; // Shift number for identification
    private LocalDateTime estimatedTime; // Estimated time for the shift
    private String status;  // Current status of the shift
    private UserResponseDTO user; // Simplified user details for the shift
    private HospitalShiftResponseDTO hospitalId; // ID of the hospital associated with the shift
    private EpsShiftResponseDTO epsId;  // ID of the EPS (health provider) associated with the shift
}
