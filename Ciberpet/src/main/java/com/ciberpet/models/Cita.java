package com.ciberpet.models;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_CITA")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
	@Id
	@Column(name = "idCita")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCita;
	
	@Column(name = "fecha_cita")
	private String fecha_cita;
	
	@Column(name = "hora_cita")
	private String hora_cita;
	
	@ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idServicio")
    private Servicio servicio;
    
    @Column(name = "Motivo") 
    private String motivo;
}
