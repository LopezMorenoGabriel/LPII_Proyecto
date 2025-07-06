package com.ciberpet.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ciberpet.models.Venta;

public interface IVentaRepository extends JpaRepository<Venta, Integer> {
	List<Venta> findAllByOrderByIdVentaDesc();
	
	@Query("SELECT v FROM Venta v " +
		       "JOIN FETCH v.usuario u " +
		       "LEFT JOIN FETCH v.detalles d " +
		       "LEFT JOIN FETCH d.producto p " +
		       "WHERE v.idVenta = :id")
		Optional<Venta> findVentaConDetalles(@Param("id") Integer id);

}
