package com.ciberpet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ciberpet.models.Producto;

public interface IProductoRepository extends JpaRepository<Producto, String> {
	
	List<Producto> findAllByOrderByIdProductoDesc();
	
	List<Producto> findByCategoriaDescripcion(String descripcion);

	@Query("""
			select p from Producto p
			where
				(:idCategoria is null or p.categoria.idCategoria = :idCategoria)
			order by
				p.idProducto desc
			""")
	List<Producto> findAllWithFilters(@Param("idCategoria") Integer idCategoria);
}
