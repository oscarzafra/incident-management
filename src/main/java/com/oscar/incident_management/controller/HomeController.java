package com.oscar.incident_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/client")
    public String client() {
        return "redirect:/incidences";
    }

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/technician")
    public String technician() {
        return "redirect:/technician/incidences";
    }
}