package com.ciberpet.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoItem {
    private Producto producto;
    private int cantidad;

    public double getSubtotal() {
        return producto.getPrecio() * cantidad; 
 }
}
