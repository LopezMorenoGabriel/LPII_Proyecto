package com.ciberpet.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_CATEGORIA")
@Getter
@Setter
public class Categoria {
	@Id
	@Column(name = "idCategoria")
	private int idCategoria;
	@Column(name = "descrip_categoria")
	private String descrip_categoria;
}
