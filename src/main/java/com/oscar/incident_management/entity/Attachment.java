package com.oscar.incident_management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "stored_filename", nullable = false)
    private String storedFilename;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "incidence_id", nullable = false)
    private Incidence incidence;

    public Attachment() {
    }

    public Long getId() {
        return id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getStoredFilename() {
        return storedFilename;
    }

    public String getFilePath() {
        return filePath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public Incidence getIncidence() {
        return incidence;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public void setStoredFilename(String storedFilename) {
        this.storedFilename = storedFilename;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public void setIncidence(Incidence incidence) {
        this.incidence = incidence;
    }
}