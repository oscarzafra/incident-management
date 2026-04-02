package com.oscar.incident_management.controller;

import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.service.IncidenceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/incidences")
public class IncidenceController {

    private final IncidenceService incidenceService;

    public IncidenceController(IncidenceService incidenceService) {
        this.incidenceService = incidenceService;
    }

    @GetMapping
    public String listIncidences(Model model, Authentication authentication,
                                 @RequestParam(value = "success", required = false) String success) {
        model.addAttribute("incidences", incidenceService.findByClientUsername(authentication.getName()));
        model.addAttribute("success", success);
        return "incidences/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("incidence", new Incidence());
        return "incidences/create";
    }

    @PostMapping
    public String createIncidence(@ModelAttribute Incidence incidence, Authentication authentication) {
        incidenceService.save(incidence, authentication.getName());
        return "redirect:/incidences?success=created";
    }
}