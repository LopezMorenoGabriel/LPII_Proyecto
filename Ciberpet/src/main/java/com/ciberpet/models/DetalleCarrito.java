package com.ciberpet.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DetalleCarrito {
	private int idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double importe;
}
