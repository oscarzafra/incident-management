package com.oscar.incident_management.service;

import com.oscar.incident_management.entity.*;
import com.oscar.incident_management.repository.StatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusHistoryService {

    private final StatusHistoryRepository statusHistoryRepository;

    public StatusHistoryService(StatusHistoryRepository statusHistoryRepository) {
        this.statusHistoryRepository = statusHistoryRepository;
    }

    public void register(Incidence incidence, IncidenceStatus previous, IncidenceStatus next, User changedBy) {
        StatusHistory history = new StatusHistory();
        history.setIncidence(incidence);
        history.setPreviousStatus(previous);
        history.setNewStatus(next);
        history.setChangedBy(changedBy);
        history.setChangedAt(LocalDateTime.now());
        statusHistoryRepository.save(history);
    }

    public List<StatusHistory> findByIncidenceId(Long incidenceId) {
        return statusHistoryRepository.findByIncidence_IdOrderByChangedAtDesc(incidenceId);
    }
}