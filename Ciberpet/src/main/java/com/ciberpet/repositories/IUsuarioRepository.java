package com.ciberpet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ciberpet.models.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Usuario findByCorreoAndContrasena(String correo, String contrasena);
	
	List<Usuario> findAllByOrderByIdUsuarioDesc();
	
	List<Usuario> findByTipo_IdTipo(int idTipo);

}
