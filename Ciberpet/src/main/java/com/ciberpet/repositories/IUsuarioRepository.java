package com.ciberpet.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ciberpet.models.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByCorreo(String correo);

	Usuario findByTelefono(String telefono);
	
	List<Usuario> findByTipo_IdTipoAndIdEstadoTrue(int idTipo);
	
	List<Usuario> findAllByOrderByIdUsuarioDesc();
	
	List<Usuario> findByTipo_IdTipo(int idTipo);
	
	boolean existsByCorreo(String correo);
	
	boolean existsByTelefono(String telefono);
	
	@Query("""
		SELECT u FROM Usuario u
		WHERE u.tipo.idTipo = 2
		  AND (:idEstado IS NULL OR u.idEstado = :idEstado)
		ORDER BY u.idUsuario DESC
	""")
	List<Usuario> findAllWithFilters(@Param("idEstado") Boolean idEstado);

	long countByTipo_IdTipoAndFechaRegistroBetween(int tipo, LocalDate inicio, LocalDate fin);

}
