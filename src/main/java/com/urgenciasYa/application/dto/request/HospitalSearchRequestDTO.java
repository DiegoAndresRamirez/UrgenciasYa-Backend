package com.urgenciasYa.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalSearchRequestDTO {
    private String eps;
    private String town;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
