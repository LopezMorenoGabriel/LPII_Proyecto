package com.ciberpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ciberpet.dtos.DetalleCompraId;
import com.ciberpet.models.DetCompra;

public interface IDetCompraRespository extends JpaRepository<DetCompra, DetalleCompraId>{

}
