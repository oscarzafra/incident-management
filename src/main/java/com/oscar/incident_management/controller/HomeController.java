package com.oscar.incident_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/technician")
    public String technician() {
        return "technician";
    }

    @GetMapping("/client")
    public String client() {
        return "client";
    }
}