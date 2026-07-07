package com.ai_interview_assistant.ai.interview.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
public class DocumentEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String documentId;

    private String fileName;

    private String documentType;

    private String uploadedBy;

    private LocalDateTime uploadedAt;

    private boolean deleted = false;
}