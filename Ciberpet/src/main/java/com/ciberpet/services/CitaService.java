package com.ciberpet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberpet.dtos.CitaFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Cita;
import com.ciberpet.repositories.ICitaRepository;

@Service
public class CitaService {

	@Autowired
	private ICitaRepository _citaRepository;
	
	public List<Cita> search(CitaFilter filtro) {
		return _citaRepository.findAllWithFilters(filtro.getIdServicio(),filtro.getEstado());
	}
	
	public Cita getOne(int id) {
		return _citaRepository.findById(id).orElseThrow();
	}
	
	public ResultadoResponse create(Cita cita) {
        try {
            Cita registrada = _citaRepository.save(cita);
            String mensaje = String.format("Cita con ID %d registrada", registrada.getIdCita());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al registrar cita: " + ex.getMessage());
        }
    }

    public ResultadoResponse update(Cita cita) {
        try {
            Cita actualizada = _citaRepository.save(cita);
            String mensaje = String.format("Cita con ID %d actualizada", actualizada.getIdCita());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al actualizar cita: " + ex.getMessage());
        }
    }

    public ResultadoResponse delete(int id) {
        try {
            _citaRepository.deleteById(id);
            return new ResultadoResponse(true, "Cita eliminada correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al eliminar cita: " + ex.getMessage());
        }
    }
}
