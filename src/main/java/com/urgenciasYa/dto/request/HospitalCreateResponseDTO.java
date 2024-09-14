package com.urgenciasYa.dto.request;

import com.urgenciasYa.model.Eps;
import com.urgenciasYa.model.Towns;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospitalCreateResponseDTO {
    private String url_image;
    private String phone_number;
    private String name;
    private Float rating;
    private Integer morning_peak;
    private Integer afternoon_peak;
    private Integer night_peak;
    private String howtogetthere;
    private Towns town_id; 
    private List<Eps> eps_id;
    private Integer latitude;
    private Integer longitude;
}
