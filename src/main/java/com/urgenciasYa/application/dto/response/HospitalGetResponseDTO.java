package com.urgenciasYa.application.dto.response;

import com.urgenciasYa.domain.model.Eps;
import com.urgenciasYa.domain.model.Towns;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalGetResponseDTO {
    private String url_image;
    private String phone_number;
    private String name;
    private Float rating;
    private Integer morning_peak;
    private Integer afternoon_peak;
    private Integer night_peak;
    private String howtogetthere;
    private TownsDTO town_id;
    private List<EpsResponseDTO> eps_id;
    private Float latitude;
    private Float longitude;
}
