package com.aiagent.enterprise_ai_agent.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String documentType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private Integer chunkCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    public void prePersist() {
        uploadedAt = LocalDateTime.now();
    }

}