package com.urgenciasYa.application.dto.response;

import com.urgenciasYa.domain.model.Eps;
import com.urgenciasYa.domain.model.Towns;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/*
 * Data Transfer Object (DTO) for creating a new hospital.
 * It contains detailed information about a hospital, including its image, contact details,
 * name, ratings, peak hours, location, and associated EPS and town.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalCreateResponseDTO {
    private String url_image;
    private String phone_number;
    private String name;
    private Float rating;
    private Integer morning_peak; // Morning peak hours for patient influx
    private Integer afternoon_peak; // Afternoon peak hours for patient influx
    private Integer night_peak; // Night peak hours for patient influx
    private String howtogetthere; // Instructions on how to reach the hospital
    private Towns town_id; 
    private List<Eps> eps_id;
    private Float latitude;
    private Float longitude;
}
