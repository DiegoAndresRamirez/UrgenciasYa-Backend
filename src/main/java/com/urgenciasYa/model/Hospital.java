package com.urgenciasYa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
