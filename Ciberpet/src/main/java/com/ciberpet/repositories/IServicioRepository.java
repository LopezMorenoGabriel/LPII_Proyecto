package com.ciberpet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ciberpet.models.Servicio;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Integer>{

}
