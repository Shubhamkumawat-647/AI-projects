package com.aiagent.enterprise_ai_agent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.DocumentEntity;
import com.aiagent.enterprise_ai_agent.entity.DocumentStatus;
import com.aiagent.enterprise_ai_agent.entity.User;

public interface DocumentRepository
        extends JpaRepository<DocumentEntity, Long> {

    /**
     * Find document by public fileId.
     */
    Optional<DocumentEntity> findByFileId(String fileId);

    /**
     * List all active documents of a user.
     */
    Page<DocumentEntity> findByUserAndStatusNot(
            User user,
            DocumentStatus status,
            Pageable pageable
    );

    /**
     * List all documents of a conversation.
     */
    List<DocumentEntity> findByConversationAndStatusNotOrderByUploadedAtDesc(
            Conversation conversation,
            DocumentStatus status
    );

    /**
     * Duplicate detection.
     */
    boolean existsByUserAndOriginalFileNameAndFileSizeAndStatusNot(
            User user,
            String originalFileName,
            Long fileSize,
            DocumentStatus status
    );

    /**
     * Count active documents.
     */
    long countByUserAndStatusNot(
            User user,
            DocumentStatus status
    );
    
    Optional<DocumentEntity> findByFileIdAndStatusNot(
            String fileId,
            DocumentStatus status);

}