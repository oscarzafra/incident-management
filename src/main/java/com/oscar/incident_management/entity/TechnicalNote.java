package com.oscar.incident_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "technical_notes")
public class TechnicalNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 2000)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "incidence_id")
    private Incidence incidence;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public TechnicalNote() {
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Incidence getIncidence() {
        return incidence;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIncidence(Incidence incidence) {
        this.incidence = incidence;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}