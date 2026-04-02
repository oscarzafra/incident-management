package com.oscar.incident_management.controller;

import com.oscar.incident_management.service.IncidenceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/technician")
public class TechnicianController {

    private final IncidenceService incidenceService;

    public TechnicianController(IncidenceService incidenceService) {
        this.incidenceService = incidenceService;
    }

    @GetMapping("/incidences")
    public String listAssignedIncidences(Model model,
                                         Authentication authentication,
                                         @RequestParam(value = "success", required = false) String success) {
        model.addAttribute("incidences", incidenceService.findByTechnicianUsername(authentication.getName()));
        model.addAttribute("success", success);
        return "technician/incidences-list";
    }

    @GetMapping("/incidences/{id}/status")
    public String showStatusForm(@PathVariable Long id, Model model) {
        model.addAttribute("incidence", incidenceService.findById(id));
        return "technician/change-status";
    }

    @PostMapping("/incidences/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        incidenceService.updateStatus(id, status);
        return "redirect:/technician/incidences?success=statusUpdated";
    }
}