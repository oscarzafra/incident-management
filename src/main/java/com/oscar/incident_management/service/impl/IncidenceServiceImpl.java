package com.oscar.incident_management.service.impl;

import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.entity.User;
import com.oscar.incident_management.repository.IncidenceRepository;
import com.oscar.incident_management.repository.UserRepository;
import com.oscar.incident_management.service.IncidenceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenceServiceImpl implements IncidenceService {

    private final IncidenceRepository incidenceRepository;
    private final UserRepository userRepository;

    public IncidenceServiceImpl(IncidenceRepository incidenceRepository, UserRepository userRepository) {
        this.incidenceRepository = incidenceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Incidence> findAll() {
        return incidenceRepository.findAll();
    }

    @Override
    public List<Incidence> findByClientUsername(String username) {
        User client = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return incidenceRepository.findByClient(client);
    }

    @Override
    public Incidence save(Incidence incidence, String username) {
        User client = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        incidence.setClient(client);
        return incidenceRepository.save(incidence);
    }
}