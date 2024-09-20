package com.urgenciasYa.application.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalShiftResponseDTO {
    private Long id;
    private String name;
}
