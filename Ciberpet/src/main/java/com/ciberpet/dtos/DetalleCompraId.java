package com.ciberpet.dtos;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class DetalleCompraId implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Integer idBoleta;
    private String idProduct;
}
