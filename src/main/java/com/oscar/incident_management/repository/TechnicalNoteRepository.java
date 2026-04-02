package com.oscar.incident_management.repository;

import com.oscar.incident_management.entity.TechnicalNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnicalNoteRepository extends JpaRepository<TechnicalNote, Long> {
    List<TechnicalNote> findByIncidence_IdOrderByCreatedAtDesc(Long incidenceId);
}