package com.urgenciasYa.application.dto.response;

import lombok.*;

import java.util.List;
import java.util.Map;

/*
 * Data Transfer Object (DTO) for retrieving hospital details.
 * This DTO contains comprehensive information about a hospital,
 * including its image, contact details, ratings, peak hours,
 * location, associated EPS, and concurrency profile.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalGetResponseDTO {
    private Long id;
    private String url_image;
    private String phone_number;
    private String name;
    private Float rating;
    private Integer morning_peak; // Morning peak hours for patient influx
    private Integer afternoon_peak; // Afternoon peak hours for patient influx
    private Integer night_peak; // Night peak hours for patient influx
    private String howtogetthere; // Instructions on how to reach the hospital
    private TownsDTO town_id; // DTO representing the town associated with the hospital
    private List<String> eps_id; // List of EPS (healthcare providers) IDs associated with the hospital
    private Map<String, Integer> concurrencyProfile; // Map representing the concurrency profile of the hospital
    private Float latitude;
    private Float longitude;
}
