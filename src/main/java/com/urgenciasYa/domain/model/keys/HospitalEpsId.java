package com.urgenciasYa.domain.model.keys;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class HospitalEpsId implements Serializable {
    private Long hospitalId;
    private Integer epsId;

    public HospitalEpsId() {}

    public HospitalEpsId(Long hospitalId, Integer epsId) {
        this.hospitalId = hospitalId;
        this.epsId = epsId;
    }

    // Getters, setters, equals, and hashCode
    // ...
}