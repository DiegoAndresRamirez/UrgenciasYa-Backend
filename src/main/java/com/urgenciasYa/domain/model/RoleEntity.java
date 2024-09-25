package com.urgenciasYa.domain.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Table(name = "role")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

// Entity representing a role in the application
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String code; // Code representing the role (e.g., "USER", "ADMIN")

    @OneToMany(mappedBy = "role")
    private Set<UserEntity> users;

}
