package com.urgenciasYa.domain.model;

import com.urgenciasYa.domain.model.keys.HospitalEpsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entity representing the relationship between Hospital and EPS (Health Insurance Company)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hospital_eps")
public class HospitalEps {
    @EmbeddedId
    private HospitalEpsId id;

    @ManyToOne
    @MapsId("hospitalId")
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @MapsId("epsId")
    @JoinColumn(name = "eps_id") // Specifies the foreign key column name in the database
    private Eps eps; // EPS associated with this record

    // Other fields if needed
}
