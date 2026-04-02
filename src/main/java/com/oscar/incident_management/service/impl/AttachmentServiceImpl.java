package com.oscar.incident_management.service.impl;

import com.oscar.incident_management.entity.Attachment;
import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.repository.AttachmentRepository;
import com.oscar.incident_management.repository.IncidenceRepository;
import com.oscar.incident_management.service.AttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final IncidenceRepository incidenceRepository;

    private final Path uploadPath = Paths.get("uploads");

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository,
                                 IncidenceRepository incidenceRepository) {
        this.attachmentRepository = attachmentRepository;
        this.incidenceRepository = incidenceRepository;

        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear la carpeta uploads", e);
        }
    }

    @Override
    public Attachment saveAttachment(Long incidenceId, MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // Obtener incidencia
            Incidence incidence = incidenceRepository.findById(incidenceId)
                    .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

            // Generar nombre único
            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID() + extension;

            // Guardar archivo físico
            Path destination = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            // Crear entidad Attachment
            Attachment attachment = new Attachment();
            attachment.setFileName(fileName);
            attachment.setOriginalFileName(originalFilename);
            attachment.setIncidence(incidence);

            return attachmentRepository.save(attachment);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }

    @Override
    public List<Attachment> findByIncidence(Long incidenceId) {
        Incidence incidence = incidenceRepository.findById(incidenceId)
                .orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

        return attachmentRepository.findByIncidence(incidence);
    }
}