package com.ciberpet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ciberpet.models.Cita;

@Repository
public interface ICitaRepository extends JpaRepository<Cita, Integer> {
	List<Cita> findAllByOrderByIdCitaDesc();

	@Query("""
			select c from Cita c
			where
				(:idServicio is null or c.servicio.idServicio = :idServicio)
				and
				(:estado = '' OR :estado IS NULL OR c.estado = :estado)
			order by
				c.idCita desc
			""")
	List<Cita> findAllWithFilters(@Param("idServicio") Integer idServicio,@Param("estado") String estado);
}
