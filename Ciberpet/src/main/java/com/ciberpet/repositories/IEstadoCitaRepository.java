package com.ciberpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciberpet.models.EstadoCita;

public interface IEstadoCitaRepository extends JpaRepository<EstadoCita, Integer> {
}
