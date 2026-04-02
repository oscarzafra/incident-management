package com.oscar.incident_management.controller;

import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.entity.User;
import com.oscar.incident_management.repository.UserRepository;
import com.oscar.incident_management.service.IncidenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final IncidenceService incidenceService;
    private final UserRepository userRepository;

    public AdminController(IncidenceService incidenceService, UserRepository userRepository) {
        this.incidenceService = incidenceService;
        this.userRepository = userRepository;
    }

    @GetMapping("/incidences")
    public String listAllIncidences(Model model,
                                    @RequestParam(value = "success", required = false) String success) {
        model.addAttribute("incidences", incidenceService.findAll());
        model.addAttribute("success", success);
        return "admin/incidences-list";
    }

    @GetMapping("/incidences/{id}/assign")
    public String showAssignForm(@PathVariable Long id, Model model) {
        Incidence incidence = incidenceService.findById(id);

        List<User> technicians = userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role ->
                                role.getName().equalsIgnoreCase("TECHNICIAN")
                                        || role.getName().equalsIgnoreCase("ROLE_TECHNICIAN")
                                        || role.getName().equalsIgnoreCase("TECNICO")
                                        || role.getName().equalsIgnoreCase("ROLE_TECNICO")
                        ))
                .toList();

        model.addAttribute("incidence", incidence);
        model.addAttribute("technicians", technicians);

        return "admin/assign-technician";
    }

    @PostMapping("/incidences/{id}/assign")
    public String assignTechnician(@PathVariable Long id,
                                   @RequestParam Long technicianId) {
        incidenceService.assignTechnician(id, technicianId);
        return "redirect:/admin/incidences?success=assigned";
    }
}