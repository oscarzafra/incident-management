package com.oscar.incident_management.service;

import com.oscar.incident_management.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttachmentService {
    void store(Long incidenceId, MultipartFile file) throws IOException;
    List<Attachment> findByIncidenceId(Long incidenceId);
}