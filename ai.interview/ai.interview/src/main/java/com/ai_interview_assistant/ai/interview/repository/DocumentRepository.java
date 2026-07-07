package com.ai_interview_assistant.ai.interview.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai_interview_assistant.ai.interview.entity.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity,Long>{

    Optional<DocumentEntity>findByDocumentId(String documentId);
    List<DocumentEntity>findByDeletedFalse();

}