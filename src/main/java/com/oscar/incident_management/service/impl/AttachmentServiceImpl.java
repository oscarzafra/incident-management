package com.oscar.incident_management.service.impl;

import com.oscar.incident_management.entity.Attachment;
import com.oscar.incident_management.entity.Incidence;
import com.oscar.incident_management.repository.AttachmentRepository;
import com.oscar.incident_management.service.AttachmentService;
import com.oscar.incident_management.service.IncidenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final IncidenceService incidenceService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository,
                                 IncidenceService incidenceService) {
        this.attachmentRepository = attachmentRepository;
        this.incidenceService = incidenceService;
    }

    @Override
    public void store(Long incidenceId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return;
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Solo se permiten archivos de imagen");
        }

        Incidence incidence = incidenceService.findById(incidenceId);

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename() != null ? file.getOriginalFilename() : "imagen";
        String storedFilename = UUID.randomUUID() + "_" + originalFilename;
        Path targetPath = uploadPath.resolve(storedFilename);

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        Attachment attachment = new Attachment();
        attachment.setIncidence(incidence);
        attachment.setOriginalFilename(originalFilename);
        attachment.setStoredFilename(storedFilename);
        attachment.setFilePath("/uploads/" + storedFilename);
        attachment.setUploadedAt(LocalDateTime.now());

        attachmentRepository.save(attachment);
    }

    @Override
    public List<Attachment> findByIncidenceId(Long incidenceId) {
        return attachmentRepository.findByIncidence_IdOrderByUploadedAtDesc(incidenceId);
    }
}