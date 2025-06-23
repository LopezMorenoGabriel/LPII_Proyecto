package com.ciberpet.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_SERVICIO")
public class Servicio {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicio")
    private Integer idServicio;

    @Column(name = "nom_servicio", nullable = false, length = 50)
    private String nomServicio;

    @Column(name = "descrip_servicio", nullable = false, length = 50)
    private String descripServicio;

    @Column(name = "precio_servicio", nullable = false)
    private Double precioServicio;
}
