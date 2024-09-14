package com.urgenciasYa.hexagonal.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalSearchRequestDTO {
    private String eps;
    private String town;
    private double latitude;
    private double longitude;
}
