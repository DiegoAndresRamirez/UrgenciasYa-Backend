package com.urgenciasYa.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(description = "specialized institution designed to provide comprehensive medical care and treatment to individuals with various health conditions.")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String url_image;
    @Column(length = 15, nullable = false)
    private String phone_number;
    @Column(length = 225, nullable = false)
    private String name;
    @Column(nullable = false)
    private Float rating;
    @Column(nullable = false)
    private Integer morning_peak;
    @Column(nullable = false)
    private Integer afternoon_peak;
    @Column(nullable = false)
    private Integer night_peak;
    @Column(nullable = false)
    private String howtogetthere;
    @JoinColumn( nullable = false)
    @OneToOne
    private Towns town_id;
    @OneToMany(mappedBy = "hospital")
    private List<Eps> eps_id;
    @Column(nullable = false)
    private Integer latitude;
    @Column(nullable = false)
    private Integer longitude;
}
