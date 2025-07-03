package com.ciberpet.models;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int idUser;

    @Column(name = "nombre", nullable = false, length = 60)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 45)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @Column(name = "nombreUser", nullable = false, length = 45)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUser;

    @Column(name = "correo", nullable = false, length = 100)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ingresar un correo válido")
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 45)
    @NotBlank(message = "La contraseña es obligatorio")
    @Size(min = 6, max = 45, message = "La contraseña debe tener entre 6 y 45 caracteres")
    private String contrasena;

    @Column(name = "telefono", nullable = false, length = 12)
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Column(name = "direccion", nullable = false, length = 155)
    @NotBlank(message = "la dirección es obligatorio")
    @Size(min = 5, max = 155, message = "La dirección debe tener entre 5 y 155 caracteres")
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo", nullable = false)
    private Tipo tipo;
}
