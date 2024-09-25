package com.urgenciasYa.domain.model;

import jakarta.persistence.*;
import lombok.*;

// Entity representing an emergency contact in the database
@Entity
@Table(name = "emergencyContact")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmergencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @OneToOne(mappedBy = "emergency", cascade = CascadeType.ALL, orphanRemoval = true) // Defines a one-to-one relationship with UserEntity
    private UserEntity user;
}