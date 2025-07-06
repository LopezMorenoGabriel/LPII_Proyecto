package com.ciberpet.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberpet.dtos.ClienteFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Usuario;
import com.ciberpet.models.Tipo;
import com.ciberpet.repositories.IUsuarioRepository;

@Service
public class ClienteService {

    @Autowired
    private IUsuarioRepository _usuarioRepository;

    public List<Usuario> getAll() {
        return _usuarioRepository.findByTipo_IdTipo(2); // Tipo cliente
    }

    public List<Usuario> search(ClienteFilter filtro) {
        return _usuarioRepository.findAllWithFilters(filtro.getIdEstado()); // Tipo cliente
    }

    public List<Usuario> getActivos() {
        return _usuarioRepository.findByTipo_IdTipoAndIdEstadoTrue(2);
    }

    public Usuario getOne(int id) {
        return _usuarioRepository.findById(id).orElseThrow();
    }

    public ResultadoResponse create(Usuario cliente) {
        if (_usuarioRepository.existsByCorreo(cliente.getCorreo())) {
            return new ResultadoResponse(false, "El correo ingresado ya existe.");
        }

        if (_usuarioRepository.existsByTelefono(cliente.getTelefono())) {
            return new ResultadoResponse(false, "El teléfono ingresado ya existe.");
        }

        cliente.setTipo(new Tipo());
        cliente.getTipo().setIdTipo(2); // Tipo Cliente
        cliente.setIdEstado(true);
        cliente.setFechaRegistro(LocalDate.now());

        _usuarioRepository.save(cliente);

        return new ResultadoResponse(true, "Cliente registrado correctamente");
    }

    public ResultadoResponse update(Usuario cliente) {
        try {
            if (!_usuarioRepository.existsById(cliente.getIdUsuario())) {
                return new ResultadoResponse(false, "Cliente no encontrado");
            }

            cliente.setTipo(new Tipo());
            cliente.getTipo().setIdTipo(2);
            _usuarioRepository.save(cliente);
            return new ResultadoResponse(true, "Cliente actualizado correctamente");

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al actualizar cliente: " + ex.getMessage());
        }
    }

    public ResultadoResponse cambiarEstado(int id) {
        try {
            Usuario cliente = getOne(id);
            cliente.setIdEstado(!cliente.getIdEstado());
            _usuarioRepository.save(cliente);

            String mensaje = String.format("Cliente %s %s",
                    cliente.getNombreCompleto(),
                    cliente.getIdEstado() ? "activado" : "desactivado");

            return new ResultadoResponse(true, mensaje);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al cambiar estado del cliente: " + ex.getMessage());
        }
    }

    public long countClientesDelMes() {
        LocalDate inicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fin = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return _usuarioRepository.countByTipo_IdTipoAndFechaRegistroBetween(2, inicio, fin);
    }
}
