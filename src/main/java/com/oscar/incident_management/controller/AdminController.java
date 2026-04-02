package com.oscar.incident_management.controller;

import com.oscar.incident_management.service.IncidenceService;
import com.oscar.incident_management.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final IncidenceService incidenceService;
    private final UserService userService;

    public AdminController(IncidenceService incidenceService, UserService userService) {
        this.incidenceService = incidenceService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("incidences", incidenceService.findAll());
        model.addAttribute("technicians", userService.findTechnicians());
        return "admin/dashboard";
    }

    @PostMapping("/incidences/{id}/assign")
    public String assignTechnician(@PathVariable Long id,
                                   @RequestParam Long technicianId) {
        incidenceService.assignTechnician(id, technicianId);
        return "redirect:/admin/dashboard";
    }
}