package com.urgenciasYa.domain.model.keys;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

// Class representing a composite key for Hospital and EPS (Entidad Promotora de Salud)

@Embeddable
@Getter
@Setter
public class HospitalEpsId implements Serializable {
    private Long hospitalId;
    private Integer epsId;

    // Default constructor
    public HospitalEpsId() {}

    // Constructor to initialize HospitalEpsId with hospitalId and epsId
    public HospitalEpsId(Long hospitalId, Integer epsId) {
        this.hospitalId = hospitalId;
        this.epsId = epsId;
    }

}