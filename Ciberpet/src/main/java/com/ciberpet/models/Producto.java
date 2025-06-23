package com.ciberpet.models;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "TB_PRODUCTO")
public class Producto {

    @Id
    @Column(name = "idProduct", length = 5)
    @Pattern(regexp = "P[0-9]{4}", message = "El ID del producto debe tener el formato 'P0001'")
    private String idProduct;

    @Column(name = "nombre_producto", nullable = false, length = 50)
    private String nombreProducto;

    @Column(name = "descrip_producto", nullable = false, length = 100)
    private String descripProducto;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria categoria;
}