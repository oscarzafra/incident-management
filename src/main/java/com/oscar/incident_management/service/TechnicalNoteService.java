package com.oscar.incident_management.service;

import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.entity.TechnicalNote;
import com.oscar.incident_management.entity.User;
import com.oscar.incident_management.repository.TechnicalNoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TechnicalNoteService {

    private final TechnicalNoteRepository technicalNoteRepository;
    private final IncidenceService incidenceService;
    private final UserService userService;

    public TechnicalNoteService(TechnicalNoteRepository technicalNoteRepository,
                                IncidenceService incidenceService,
                                UserService userService) {
        this.technicalNoteRepository = technicalNoteRepository;
        this.incidenceService = incidenceService;
        this.userService = userService;
    }

    public void addNote(Long incidenceId, String content, String username) {
        Incidence incidence = incidenceService.findById(incidenceId);
        User author = userService.findByUsername(username);

        TechnicalNote note = new TechnicalNote();
        note.setIncidence(incidence);
        note.setAuthor(author);
        note.setContent(content);
        note.setCreatedAt(LocalDateTime.now());

        technicalNoteRepository.save(note);
    }

    public List<TechnicalNote> findByIncidenceId(Long incidenceId) {
        return technicalNoteRepository.findByIncidence_IdOrderByCreatedAtDesc(incidenceId);
    }
}