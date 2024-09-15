package com.urgenciasYa.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalSearchRequestDTO {
    private String eps;
    private String town;
    private Double latitude;
    private Double longitude;
}
