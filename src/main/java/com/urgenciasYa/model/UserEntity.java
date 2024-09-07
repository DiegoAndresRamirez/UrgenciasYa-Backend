package com.urgenciasYa.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String eps;
    @Column(nullable = false)
    private String password;
    @Column(unique = true,nullable = false)
    private String email;
    @OneToOne
    @JoinColumn(name = "emergency_id")
    private EmergencyEntity emergency; // Relación uno a uno con otra entidad (EmergencyEntity)

    @OneToOne
    @JoinColumn(name = "role_id") // Especifica la columna de la llave foránea
    private RoleEntity role; // Relación uno a uno con otra entidad (RoleEntity)
}
