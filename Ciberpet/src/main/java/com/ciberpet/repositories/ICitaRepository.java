package com.ciberpet.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ciberpet.models.Cita;

@Repository
public interface ICitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findAllByOrderByFechaHoraCitaDesc();

    @Query("""
        SELECT c FROM Cita c
        WHERE 
            (:idServicio IS NULL OR c.servicio.idServicio = :idServicio) AND
            (:idEstadoCita IS NULL OR c.estadoCita.idEstadoCita = :idEstadoCita)
        ORDER BY c.fechaHoraCita DESC
    """)
    List<Cita> findAllWithFilters(
        @Param("idServicio") Integer idServicio,
        @Param("idEstadoCita") Integer idEstadoCita
    );

    long countByFechaHoraCitaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("""
        SELECT COALESCE(SUM(c.servicio.precio), 0)
        FROM Cita c
        WHERE c.fechaHoraCita BETWEEN :inicio AND :fin
    """)
    double sumPrecioServiciosDelMes(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
