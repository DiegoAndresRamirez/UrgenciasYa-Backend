package com.urgenciasYa.domain.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;


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

    @OneToOne(mappedBy = "emergency")
    private UserEntity user;

}
