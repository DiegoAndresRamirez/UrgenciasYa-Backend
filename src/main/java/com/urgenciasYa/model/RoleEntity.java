package com.urgenciasYa.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @Column(nullable = false)
    private String code;

}
