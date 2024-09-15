package com.urgenciasYa.domain.model;

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
@ToString
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
    @Column(nullable = false, unique = true)
    private String document;
    @OneToOne
    @JoinColumn(name = "emergency_id")
    private EmergencyEntity emergency;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
