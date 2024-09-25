package com.urgenciasYa.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data Transfer Object (DTO) for hospital search requests.
 * This class represents the criteria used to search for hospitals.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalSearchRequestDTO {
    private String eps; // The EPS (Entidades Promotoras de Salud) related to the search
    private String town; // The town in which to search for hospitals
    private Double latitude; // The latitude for geographical search criteria
    private Double longitude; // The longitude for geographical search criteria
}
