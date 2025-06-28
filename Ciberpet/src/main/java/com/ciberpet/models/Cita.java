package com.ciberpet.models;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	
	@NotNull(message = "La fecha de la cita es obligatoria")
    @FutureOrPresent(message = "La fecha de la cita no puede ser anterior a hoy")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_cita")
    private LocalDate fecha_cita;

    @NotBlank(message = "La hora de la cita es obligatoria")
    @Column(name = "hora_cita")
    private String hora_cita;

    @NotNull(message = "Debe seleccionar un usuario")
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @NotNull(message = "Debe seleccionar un servicio")
    @ManyToOne
    @JoinColumn(name = "idServicio")
    private Servicio servicio;

    @NotBlank(message = "Debe ingresar el motivo de la cita")
    @Size(max = 100, message = "El motivo no puede tener más de 100 caracteres")
    @Column(name = "Motivo")
    private String motivo;
}
