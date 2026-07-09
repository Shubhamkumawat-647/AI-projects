package com.aiagent.enterprise_ai_agent.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "documents",
        indexes = {

                @Index(
                        name = "idx_document_file_id",
                        columnList = "fileId",
                        unique = true
                ),

                @Index(
                        name = "idx_document_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_document_conversation",
                        columnList = "conversation_id"
                )

        }
)
@Getter
@Setter
@NoArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Public ID
     * Exposed to frontend.
     */
    @Column(nullable = false, unique = true, updatable = false)
    private String fileId;

    /**
     * Stored File Name
     */
    @Column(nullable = false)
    private String fileName;

    /**
     * Original Uploaded Name
     */
    @Column(nullable = false)
    private String originalFileName;

    /**
     * application/pdf
     */
    @Column(nullable = false)
    private String contentType;

    /**
     * bytes
     */
    @Column(nullable = false)
    private Long fileSize;

    /**
     * Number of chunks
     */
    @Column(nullable = false)
    private Integer chunkCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "conversation_id",
            nullable = false
    )
    private Conversation conversation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        fileId = UUID.randomUUID().toString();

        uploadedAt = LocalDateTime.now();

        updatedAt = LocalDateTime.now();

        if (status == null) {

            status = DocumentStatus.UPLOADING;

        }

    }

    @PreUpdate
    public void preUpdate() {

        updatedAt = LocalDateTime.now();

    }

}