package com.ciberpet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ciberpet.dtos.AutenticacionFilter;
import com.ciberpet.dtos.RegistroUsuarioDTO;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Tipo;
import com.ciberpet.models.Usuario;
import com.ciberpet.repositories.IUsuarioRepository;

@Service
public class AutenticacionService {

	@Autowired
	private IUsuarioRepository _usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Usuario autenticar(AutenticacionFilter filter) {
	    Usuario usuario = _usuarioRepository.findByCorreo(filter.getCorreo());

	    if (usuario == null) {
	        System.out.println("Resultado: Usuario NO encontrado.");
	        return null;
	    }

	    boolean contrasenaCoincide = passwordEncoder.matches(filter.getContrasena(), usuario.getContrasena());

	    if (!contrasenaCoincide) {
	        System.out.println("Login fallido: La contraseña no coincide.");
	        return null;
	    }

	    if (usuario.getIdEstado() == null || !usuario.getIdEstado()) {
	        System.out.println("Login fallido: El usuario está desactivado.");
	        usuario.setIdEstado(null);
	        return usuario;
	    }

	    System.out.println("Login exitoso para: " + usuario.getNombre());
	    return usuario;
	}
	

	public ResultadoResponse createFromDto(RegistroUsuarioDTO usuarioDto) {
		try {
			if (_usuarioRepository.findByCorreo(usuarioDto.getCorreo()) != null) {
				return new ResultadoResponse(false, "El correo electrónico ya está en uso.");
			}
	        if (_usuarioRepository.findByTelefono(usuarioDto.getTelefono()) != null) {
	            return new ResultadoResponse(false, "El número de teléfono ya está registrado.");
	        }

			Usuario nuevoUsuario = new Usuario();
			nuevoUsuario.setNombre(usuarioDto.getNombre());
			nuevoUsuario.setApellidos(usuarioDto.getApellidos());
			nuevoUsuario.setCorreo(usuarioDto.getCorreo());
			nuevoUsuario.setTelefono(usuarioDto.getTelefono());
			nuevoUsuario.setDireccion(usuarioDto.getDireccion());

			String contrasenaHasheada = passwordEncoder.encode(usuarioDto.getContrasena());
			nuevoUsuario.setContrasena(contrasenaHasheada);
			
			Tipo tipoCliente = new Tipo();
			tipoCliente.setIdTipo(2);
			nuevoUsuario.setTipo(tipoCliente);
			
			_usuarioRepository.save(nuevoUsuario);
			
			return new ResultadoResponse(true, "Usuario registrado correctamente");

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultadoResponse(false, "Error interno al registrar el usuario.");
		}
	}
	
}
