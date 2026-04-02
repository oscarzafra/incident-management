package com.oscar.incident_management.repository;

import com.oscar.incident_management.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
    List<StatusHistory> findByIncidence_IdOrderByChangedAtDesc(Long incidenceId);
}