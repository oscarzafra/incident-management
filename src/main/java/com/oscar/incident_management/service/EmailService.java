package com.oscar.incident_management.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendIncidenceStatusChangedEmail(String to, String incidenceTitle, String newStatus) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Actualización de incidencia");
            message.setText("La incidencia \"" + incidenceTitle + "\" ha cambiado a estado: " + newStatus);
            mailSender.send(message);
        } catch (Exception ignored) {
            // para no romper la app si el correo no está configurado todavía
        }
    }
}