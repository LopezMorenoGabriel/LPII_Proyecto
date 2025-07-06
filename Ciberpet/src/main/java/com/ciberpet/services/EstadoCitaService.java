package com.ciberpet.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ciberpet.models.EstadoCita;
import com.ciberpet.repositories.IEstadoCitaRepository;

@Service
public class EstadoCitaService {

    @Autowired
    private IEstadoCitaRepository estadoCitaRepository;

    public List<EstadoCita> getAll() {
        return estadoCitaRepository.findAll();
    }
}
