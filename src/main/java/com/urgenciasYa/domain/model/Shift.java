package com.urgenciasYa.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urgenciasYa.common.utils.enums.StatusShift;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(description = "Represents a medical appointment or shift for a user in a hospital")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The turn number is required.")
    @Column(nullable = false)
    private String shiftNumber;

    @NotNull(message = "The estimated time is required.")
    @FutureOrPresent(message = "The estimated time must be a present or future date.")
    @Column(nullable = false)
    private LocalDateTime estimatedTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusShift status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // Omitir el objeto completo en la serialización
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    @JsonIgnore // Omitir el objeto completo en la serialización
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "eps_id", nullable = false)
    @JsonIgnore // Omitir el objeto completo en la serialización
    private Eps eps;

}
