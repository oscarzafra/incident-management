package com.oscar.incident_management.repository;

import com.oscar.incident_management.entity.Attachment;
import com.oscar.incident_management.entity.Incidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findByIncidence(Incidence incidence);
}