package com.oscar.incident_management.service;

import com.oscar.incident_management.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    Attachment saveAttachment(Long incidenceId, MultipartFile file);

    List<Attachment> findByIncidence(Long incidenceId);
}