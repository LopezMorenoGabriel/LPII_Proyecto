package com.ciberpet.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCita")
    private int idCita;

    @NotNull(message = "La fecha y hora de la cita es obligatoria")
    @FutureOrPresent(message = "La cita no puede estar en el pasado")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_hora_cita", nullable = false)
    private LocalDateTime fechaHoraCita;

    @NotNull(message = "Debe seleccionar un usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @NotNull(message = "Debe seleccionar un servicio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idServicio", nullable = false)
    private Servicio servicio;

    @Size(max = 100, message = "El motivo no puede tener más de 100 caracteres")
    @Column(name = "motivo")
    private String motivo;

    @NotNull(message = "Debe asignar un estado para la cita")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEstadoCita", nullable = false)
    private EstadoCita estadoCita;
}
