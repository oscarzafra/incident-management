package com.oscar.incident_management.service.impl;

import com.oscar.incident_management.entity.*;
import com.oscar.incident_management.repository.IncidenceRepository;
import com.oscar.incident_management.service.EmailService;
import com.oscar.incident_management.service.IncidenceService;
import com.oscar.incident_management.service.StatusHistoryService;
import com.oscar.incident_management.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidenceServiceImpl implements IncidenceService {

    private final IncidenceRepository incidenceRepository;
    private final UserService userService;
    private final StatusHistoryService statusHistoryService;
    private final EmailService emailService;

    public IncidenceServiceImpl(IncidenceRepository incidenceRepository,
                                UserService userService,
                                StatusHistoryService statusHistoryService,
                                EmailService emailService) {
        this.incidenceRepository = incidenceRepository;
        this.userService = userService;
        this.statusHistoryService = statusHistoryService;
        this.emailService = emailService;
    }

    @Override
    public Incidence createForClient(Incidence incidence, String username) {
        User client = userService.findByUsername(username);
        incidence.setClient(client);
        incidence.setCreatedAt(LocalDateTime.now());
        incidence.setStatus(IncidenceStatus.ABIERTA);
        return incidenceRepository.save(incidence);
    }

    @Override
    public List<Incidence> findVisibleForUser(String username) {
        User user = userService.findByUsername(username);

        if (user.hasRole("ROLE_ADMIN")) {
            return incidenceRepository.findAllByOrderByCreatedAtDesc();
        }
        if (user.hasRole("ROLE_TECHNICIAN")) {
            return incidenceRepository.findByAssignedTechnicianOrderByCreatedAtDesc(user);
        }
        return incidenceRepository.findByClientOrderByCreatedAtDesc(user);
    }

    @Override
    public Incidence findById(Long id) {
        return incidenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));
    }

    @Override
    public Incidence findAccessibleById(Long id, String username) {
        User user = userService.findByUsername(username);
        Incidence incidence = findById(id);

        boolean isAdmin = user.hasRole("ROLE_ADMIN");
        boolean isOwner = incidence.getClient() != null && incidence.getClient().getId().equals(user.getId());
        boolean isTechnician = incidence.getAssignedTechnician() != null
                && incidence.getAssignedTechnician().getId().equals(user.getId());

        if (!isAdmin && !isOwner && !isTechnician) {
            throw new AccessDeniedException("No autorizado");
        }

        return incidence;
    }

    @Override
    public void assignTechnician(Long incidenceId, Long technicianId) {
        Incidence incidence = findById(incidenceId);
        User technician = userService.findAll().stream()
                .filter(user -> user.getId().equals(technicianId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        incidence.setAssignedTechnician(technician);
        incidenceRepository.save(incidence);
    }

    @Override
    public void changeStatus(Long incidenceId, IncidenceStatus newStatus, String username) {
        Incidence incidence = findAccessibleById(incidenceId, username);
        User user = userService.findByUsername(username);

        IncidenceStatus previousStatus = incidence.getStatus();
        if (previousStatus == newStatus) {
            return;
        }

        incidence.setStatus(newStatus);
        incidenceRepository.save(incidence);

        statusHistoryService.register(incidence, previousStatus, newStatus, user);

        if (incidence.getClient() != null && incidence.getClient().getEmail() != null) {
            emailService.sendIncidenceStatusChangedEmail(
                    incidence.getClient().getEmail(),
                    incidence.getTitle(),
                    newStatus.name()
            );
        }
    }
}