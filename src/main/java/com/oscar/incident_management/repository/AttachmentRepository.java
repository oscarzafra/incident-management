package com.oscar.incident_management.repository;

import com.oscar.incident_management.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByIncidence_IdOrderByUploadedAtDesc(Long incidenceId);
}