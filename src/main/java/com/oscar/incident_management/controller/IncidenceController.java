package com.oscar.incident_management.controller;

import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.service.FileStorageService;
import com.oscar.incident_management.service.IncidenceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/incidences")
public class IncidenceController {

    private final IncidenceService incidenceService;
    private final FileStorageService fileStorageService;

    public IncidenceController(IncidenceService incidenceService,
                               FileStorageService fileStorageService) {
        this.incidenceService = incidenceService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public String listIncidences(Model model,
                                 Authentication authentication,
                                 @RequestParam(value = "success", required = false) String success) {
        model.addAttribute("incidences",
                incidenceService.findByClientUsername(authentication.getName()));
        model.addAttribute("success", success);
        return "incidences/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("incidence", new Incidence());
        return "incidences/create";
    }

    @PostMapping
    public String createIncidence(@ModelAttribute Incidence incidence,
                                  @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                  Authentication authentication) {

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = fileStorageService.saveFile(imageFile);
            incidence.setImagePath(fileName);
        }

        incidenceService.save(incidence, authentication.getName());
        return "redirect:/incidences?success=created";
    }
}