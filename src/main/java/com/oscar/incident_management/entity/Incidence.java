package com.oscar.incident_management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incidences")
public class Incidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 3000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidenceStatus status = IncidenceStatus.OPEN;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private User technician;

    @OneToMany(mappedBy = "incidence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "incidence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatusHistory> statusHistory = new ArrayList<>();

    @OneToMany(mappedBy = "incidence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicalNote> technicalNotes = new ArrayList<>();

    public Incidence() {
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IncidenceStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User getClient() {
        return client;
    }

    public User getTechnician() {
        return technician;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public List<StatusHistory> getStatusHistory() {
        return statusHistory;
    }

    public List<TechnicalNote> getTechnicalNotes() {
        return technicalNotes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(IncidenceStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setTechnician(User technician) {
        this.technician = technician;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setStatusHistory(List<StatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public void setTechnicalNotes(List<TechnicalNote> technicalNotes) {
        this.technicalNotes = technicalNotes;
    }
}