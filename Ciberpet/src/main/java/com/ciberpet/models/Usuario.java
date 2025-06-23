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
    private Integer idUsuario;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 45)
    private String apellidos;

    @Column(name = "nombreUser", nullable = false, length = 45)
    private String nombreUser;

    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @Column(name = "contrasena", nullable = false, length = 45)
    private String contrasena;

    @Column(name = "telefono", nullable = false, length = 12)
    private String telefono;

    @Column(name = "direccion", nullable = false, length = 155)
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo", nullable = false)
    private Tipo tipo;
}
