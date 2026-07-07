package com.ai_interview_assistant.ai.interview.service;
import java.util.List;

import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.ai_interview_assistant.ai.interview.entity.DocumentEntity;
import com.ai_interview_assistant.ai.interview.repository.DocumentRepository;

import jakarta.transaction.Transactional;

@Service
public class DocumentService {

    private final VectorStore vectorStore;
    private final DocumentRepository documentRepository;

    public DocumentService(VectorStore vectorStore,DocumentRepository documentRepository){

        this.vectorStore = vectorStore;
        this.documentRepository=documentRepository;

    }

    public List<DocumentEntity> getAllDocuments(){

        return documentRepository.findByDeletedFalse();

    }
    public DocumentEntity getDocument(
            String documentId){

        return documentRepository
                .findByDocumentId(documentId)

                .orElseThrow(()->
                new RuntimeException(
                "Document not found"));

    }
    @Transactional
    public void deleteDocument(
            String documentId){

        DocumentEntity document =getDocument(documentId);
        vectorStore.delete(List.of(documentId));
        document.setDeleted(true);

        documentRepository.save(document);

    }
}