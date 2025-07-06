package com.ciberpet.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberpet.dtos.CitaFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Cita;
import com.ciberpet.models.EstadoCita;
import com.ciberpet.repositories.ICitaRepository;
import com.ciberpet.repositories.IEstadoCitaRepository;

@Service
public class CitaService {

    @Autowired
    private ICitaRepository _citaRepository;

    @Autowired
    private IEstadoCitaRepository estadoCitaRepository;

    public List<Cita> listar() {
        return _citaRepository.findAllByOrderByFechaHoraCitaDesc();
    }

    public List<Cita> search(CitaFilter filtro) {
        return _citaRepository.findAllWithFilters(filtro.getIdServicio(), filtro.getIdEstadoCita());
    }

    public Cita getOne(Integer id) {
        Optional<Cita> optional = _citaRepository.findById(id);
        return optional.orElseThrow(() -> new RuntimeException("La cita con ID " + id + " no fue encontrada."));
    }

    public ResultadoResponse create(Cita cita) {
        try {
            Cita registrada = _citaRepository.save(cita);
            String mensaje = String.format("Cita con ID %d registrada correctamente.", registrada.getIdCita());
            return new ResultadoResponse(true, mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al registrar cita: " + ex.getMessage());
        }
    }

    public ResultadoResponse update(Cita cita) {
        try {
            if (!_citaRepository.existsById(cita.getIdCita())) {
                return new ResultadoResponse(false, "La cita no existe.");
            }

            Cita actualizada = _citaRepository.save(cita);
            String mensaje = String.format("Cita con ID %d actualizada correctamente.", actualizada.getIdCita());
            return new ResultadoResponse(true, mensaje);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al actualizar cita: " + ex.getMessage());
        }
    }

    public ResultadoResponse actualizarEstado(Integer idCita, EstadoCita nuevoEstado) {
        try {
            Cita cita = getOne(idCita);
            cita.setEstadoCita(nuevoEstado);
            _citaRepository.save(cita);
            return new ResultadoResponse(true, "Estado actualizado correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al actualizar estado: " + ex.getMessage());
        }
    }

    public List<EstadoCita> getEstados() {
        return estadoCitaRepository.findAll();
    }

    public long countCitasDelMes() {
        LocalDateTime inicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fin = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        return _citaRepository.countByFechaHoraCitaBetween(inicio, fin);
    }

    public double calcularIngresosDelMes() {
        LocalDateTime inicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fin = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        return _citaRepository.sumPrecioServiciosDelMes(inicio, fin);
    }
}
