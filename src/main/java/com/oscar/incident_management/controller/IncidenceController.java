package com.oscar.incident_management.controller;

import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.entity.IncidenceStatus;
import com.oscar.incident_management.service.AttachmentService;
import com.oscar.incident_management.service.IncidenceService;
import com.oscar.incident_management.service.StatusHistoryService;
import com.oscar.incident_management.service.TechnicalNoteService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/incidences")
public class IncidenceController {

    private final IncidenceService incidenceService;
    private final StatusHistoryService statusHistoryService;
    private final TechnicalNoteService technicalNoteService;
    private final AttachmentService attachmentService;

    public IncidenceController(IncidenceService incidenceService,
                               StatusHistoryService statusHistoryService,
                               TechnicalNoteService technicalNoteService,
                               AttachmentService attachmentService) {
        this.incidenceService = incidenceService;
        this.statusHistoryService = statusHistoryService;
        this.technicalNoteService = technicalNoteService;
        this.attachmentService = attachmentService;
    }

    @GetMapping
    public String list(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("incidences", incidenceService.findVisibleForUser(user.getUsername()));
        return "incidences/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("incidence", new Incidence());
        return "incidences/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("incidence") Incidence incidence,
                         BindingResult result,
                         @AuthenticationPrincipal User user,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("incidence", incidence);
            return "incidences/create";
        }

        incidenceService.createForClient(incidence, user.getUsername());
        return "redirect:/incidences";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Model model,
                         @AuthenticationPrincipal User user) {
        model.addAttribute("incidence", incidenceService.findAccessibleById(id, user.getUsername()));
        model.addAttribute("statusHistory", statusHistoryService.findByIncidenceId(id));
        model.addAttribute("technicalNotes", technicalNoteService.findByIncidenceId(id));
        model.addAttribute("attachments", attachmentService.findByIncidenceId(id));
        model.addAttribute("statuses", IncidenceStatus.values());
        return "incidences/detail";
    }

    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Long id,
                               @RequestParam IncidenceStatus status,
                               @AuthenticationPrincipal User user) {
        incidenceService.changeStatus(id, status, user.getUsername());
        return "redirect:/incidences/" + id;
    }

    @PostMapping("/{id}/notes")
    public String addNote(@PathVariable Long id,
                          @RequestParam String content,
                          @AuthenticationPrincipal User user) {
        technicalNoteService.addNote(id, content, user.getUsername());
        return "redirect:/incidences/" + id;
    }

    @PostMapping("/{id}/attachments")
    public String uploadAttachment(@PathVariable Long id,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        attachmentService.store(id, file);
        return "redirect:/incidences/" + id;
    }
}