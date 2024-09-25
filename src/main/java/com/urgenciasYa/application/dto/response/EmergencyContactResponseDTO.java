package com.urgenciasYa.application.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data Transfer Object (DTO) representing the response for an emergency contact.
 * This class captures the data that will be sent back to the client when querying emergency contact information.
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyContactResponseDTO {
    @Column(nullable = false) // Ensures this field is not nullable in the database
    private Long id; // Unique identifier for the emergency contact

    @Column(nullable = false) // Ensures this field is not nullable in the database
    private String name; // Name of the emergency contact

    @Column(nullable = false) // Ensures this field is not nullable in the database
    private String phone; // Phone number of the emergency contact
}
