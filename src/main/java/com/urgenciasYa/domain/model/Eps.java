package com.urgenciasYa.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// Entity representing a health insurance company (EPS)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Health insurance company")

public class Eps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "eps") // Defines a one-to-many relationship with HospitalEps
    private List<HospitalEps> hospitalsEps; // List of hospitals associated with this health insurance company
}
