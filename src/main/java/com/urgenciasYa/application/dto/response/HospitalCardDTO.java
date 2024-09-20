package com.urgenciasYa.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "specialized institution designed to provide comprehensive medical care and treatment to individuals with various health conditions.")

public class HospitalCardDTO {
    private Long id;
    private String url_image;
    private String phone_number;
    private String name;
    private Float rating;
    private String howtogetthere;
    private String nameTown;
    private String nameEps;
    private Map<String, Integer> concurrencyProfile;

}
