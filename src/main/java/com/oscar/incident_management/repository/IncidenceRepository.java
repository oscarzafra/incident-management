package com.oscar.incident_management.repository;

import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenceRepository extends JpaRepository<Incidence, Long> {
    List<Incidence> findByClient(User client);
    List<Incidence> findByTechnician(User technician);
}