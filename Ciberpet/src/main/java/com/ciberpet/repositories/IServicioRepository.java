package com.ciberpet.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ciberpet.models.Servicio;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer>{

    List<Servicio> findAllByIdEstado(Boolean idEstado);

    List<Servicio> findByIdEstadoTrue();
}
