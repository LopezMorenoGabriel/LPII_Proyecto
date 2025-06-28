package com.ciberpet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberpet.models.Categoria;
import com.ciberpet.repositories.ICategoriaRepository;


@Service
public class CategoriaService {
	
	@Autowired
	private ICategoriaRepository _categoriaRepository;
	
	public List<Categoria> getAll() {
		return _categoriaRepository.findAll();
	}
}
