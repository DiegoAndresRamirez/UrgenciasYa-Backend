package com.urgenciasYa.domain.model;

import com.urgenciasYa.domain.model.keys.HospitalEpsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "eps_id")
    private Eps eps;

    // Other fields if needed
}
