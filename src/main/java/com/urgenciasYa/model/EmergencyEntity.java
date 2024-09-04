package com.urgenciasYa.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

public class EmergencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;

}
