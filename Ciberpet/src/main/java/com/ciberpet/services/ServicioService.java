package com.ciberpet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberpet.models.Servicio;

import com.ciberpet.repositories.IServicioRepository;

@Service
public class ServicioService {
	@Autowired
	private IServicioRepository _servicioRepository;
	
	public List<Servicio> getAll() {
		return _servicioRepository.findAll();
	}
}
