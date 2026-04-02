package com.oscar.incident_management.service;

import com.oscar.incident_management.entity.Incidence;
import java.util.List;

public interface IncidenceService {

    List<Incidence> findAll();

    List<Incidence> findByClientUsername(String username);

    Incidence save(Incidence incidence, String username);

    Incidence findById(Long id);

    Incidence assignTechnician(Long incidenceId, Long technicianId);
}