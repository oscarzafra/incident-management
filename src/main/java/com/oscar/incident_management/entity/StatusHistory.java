package com.oscar.incident_management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "status_history")
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "incidence_id")
    private Incidence incidence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidenceStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidenceStatus newStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "changed_by_id")
    private User changedBy;

    @Column(nullable = false)
    private LocalDateTime changedAt = LocalDateTime.now();

    public StatusHistory() {
    }

    public Long getId() {
        return id;
    }

    public Incidence getIncidence() {
        return incidence;
    }

    public IncidenceStatus getPreviousStatus() {
        return previousStatus;
    }

    public IncidenceStatus getNewStatus() {
        return newStatus;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIncidence(Incidence incidence) {
        this.incidence = incidence;
    }

    public void setPreviousStatus(IncidenceStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    public void setNewStatus(IncidenceStatus newStatus) {
        this.newStatus = newStatus;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}