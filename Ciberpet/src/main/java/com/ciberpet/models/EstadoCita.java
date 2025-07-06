package com.ciberpet.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_ESTADO_CITA")
@Getter
@Setter
public class EstadoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstadoCita")
    private Integer idEstadoCita;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;
}
