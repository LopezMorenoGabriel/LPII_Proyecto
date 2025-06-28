package com.ciberpet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberpet.dtos.AutentacionFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Usuario;
import com.ciberpet.repositories.IUsuarioRepository;

@Service
public class AutenticacionService {

	@Autowired
	private IUsuarioRepository _usuarioRepository;

	public Usuario autenticar(AutentacionFilter filter) {
		return _usuarioRepository.findByCorreoAndContrasena(filter.getCorreo(), filter.getContrasena());
	}
	
	public ResultadoResponse create(Usuario usuario) {
        try {
            _usuarioRepository.save(usuario);
            return new ResultadoResponse(true, "Usuario registrado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultadoResponse(false, "Error al registrar usuario: " + e.getMessage());
        }
    }
}
