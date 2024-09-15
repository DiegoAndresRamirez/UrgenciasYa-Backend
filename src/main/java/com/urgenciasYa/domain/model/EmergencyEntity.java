package com.urgenciasYa.domain.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "emergencyContact")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;

    @OneToOne(mappedBy = "emergency")
    private UserEntity user;

}
