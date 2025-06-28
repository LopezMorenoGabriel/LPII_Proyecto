package com.ciberpet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ciberpet.models.Usuario;
import com.ciberpet.repositories.IUsuarioRepository;

@Service
public class ClienteService {
	@Autowired
	private IUsuarioRepository _usuarioRepository;
	
	public List<Usuario> getAll() {
		return _usuarioRepository.findByTipo_IdTipo(2);
	}
	
	public Usuario getOne(int id) {
		return _usuarioRepository.findById(id).orElseThrow();
	}
}
