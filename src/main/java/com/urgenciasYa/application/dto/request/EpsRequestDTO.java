package com.urgenciasYa.application.dto.request;

import lombok.*;

/*
 * Data Transfer Object (DTO) for EPS (Entidades Promotoras de Salud) requests.
 * This class represents the information required to create or update an EPS entity.
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpsRequestDTO {
    private String name; // The name of the EPS
}
