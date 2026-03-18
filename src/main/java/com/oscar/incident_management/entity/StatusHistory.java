package com.oscar.incident_management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_history")
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidenceStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidenceStatus newStatus;

    @Column(nullable = false)
    private LocalDateTime changedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "changed_by_id", nullable = false)
    private User changedBy;

    @ManyToOne
    @JoinColumn(name = "incidence_id", nullable = false)
    private Incidence incidence;

    public StatusHistory() {
    }

    public Long getId() {
        return id;
    }

    public IncidenceStatus getPreviousStatus() {
        return previousStatus;
    }

    public IncidenceStatus getNewStatus() {
        return newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public Incidence getIncidence() {
        return incidence;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPreviousStatus(IncidenceStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public void setNewStatus(IncidenceStatus newStatus) {
        this.newStatus = newStatus;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public void setIncidence(Incidence incidence) {
        this.incidence = incidence;
    }
}