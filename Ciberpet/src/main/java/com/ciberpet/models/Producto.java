package com.ciberpet.models;

import java.time.LocalDate;
import java.math.BigDecimal;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "TB_PRODUCTO")
public class Producto {

    @Id
    @Pattern(regexp = "P[0-9]{4}", message = "El ID del producto debe tener el formato 'P0001'")
    @Column(name = "idProduct", length = 5)
    private String idProducto;

    @Column(name = "nombre_producto", nullable = false, length = 50)
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombreProducto;

    @Column(name = "descrip_producto", nullable = false, length = 100)
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 100, message = "La descripción debe tener entre 5 y 100 caracteres")
    private String descripProducto;

    @Column(name = "precio", nullable = false)
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", inclusive = true, message = "El precio debe ser mayor que cero")
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Column(name = "estado", nullable = false)
    private Boolean idEstado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria", nullable = false)
    @NotNull(message = "Debe seleccionar una categoría")
    private Categoria categoria;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
}
