package com.oscar.incident_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incidences")
public class Incidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(max = 2000)
    @Column(nullable = false, length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidenceStatus status = IncidenceStatus.ABIERTA;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "assigned_technician_id")
    private User assignedTechnician;

    @OneToMany(mappedBy = "incidence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatusHistory> statusHistory = new ArrayList<>();

    @OneToMany(mappedBy = "incidence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicalNote> technicalNotes = new ArrayList<>();

    @OneToMany(mappedBy = "incidence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    public Incidence() {
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

    public User getClient() {
        return client;
    }

    public User getAssignedTechnician() {
        return assignedTechnician;
    }

    public List<StatusHistory> getStatusHistory() {
        return statusHistory;
    }

    public List<TechnicalNote> getTechnicalNotes() {
        return technicalNotes;
    }

    public List<Attachment> getAttachments() {
        return attachments;
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

    public void setClient(User client) {
        this.client = client;
    }

    public void setAssignedTechnician(User assignedTechnician) {
        this.assignedTechnician = assignedTechnician;
    }

    public void setStatusHistory(List<StatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public void setTechnicalNotes(List<TechnicalNote> technicalNotes) {
        this.technicalNotes = technicalNotes;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}