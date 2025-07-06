package com.ciberpet.models;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "TB_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "nombre", nullable = false, length = 60)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 45)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @Column(name = "correo", nullable = false, length = 100)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ingresar un correo válido")
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 60)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "Debe tener 6 caracteres como mínimo")
    private String contrasena;

    @Column(name = "telefono", nullable = false, length = 12)
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Column(name = "direccion", nullable = false, length = 155)
    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 155, message = "La dirección debe tener entre 5 y 155 caracteres")
    private String direccion;

    @Column(name = "estado")
    private Boolean idEstado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo", nullable = false)
    private Tipo tipo;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    public String getNombreCompleto() {
        return this.nombre + " " + this.apellidos;
    }
}
