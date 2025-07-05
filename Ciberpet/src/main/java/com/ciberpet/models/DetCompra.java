package com.ciberpet.models;

import com.ciberpet.dtos.DetalleCompraId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_DET_COMPRA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetCompra {

	@EmbeddedId
	private DetalleCompraId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idBoleta")
	@JoinColumn(name = "idBoleta")
	private Compra compra;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idProduct")
	@JoinColumn(name = "idProduct")
	private Producto producto;

	private Integer cantidad;

	@Column(name = "Total")
	private Double total;
}
