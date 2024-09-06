package com.urgenciasYa.dto.response;

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

public class HospitalCardDTO {
    private String url_image;
    private String phone_number;
    private String name;
    private Float rating;
    private String howtogetthere;
    private String nameTown;
    private String nameEps;
    private Map<String, Integer> concurrencyProfile;

}
