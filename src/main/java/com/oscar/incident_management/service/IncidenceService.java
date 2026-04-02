package com.oscar.incident_management.service;

import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.entity.IncidenceStatus;

import java.util.List;

public interface IncidenceService {
    Incidence createForClient(Incidence incidence, String username);
    List<Incidence> findVisibleForUser(String username);
    Incidence findById(Long id);
    Incidence findAccessibleById(Long id, String username);
    void assignTechnician(Long incidenceId, Long technicianId);
    void changeStatus(Long incidenceId, IncidenceStatus newStatus, String username);
}