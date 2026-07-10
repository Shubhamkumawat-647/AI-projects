package com.aiagent.enterprise_ai_agent.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.DocumentEntity;
import com.aiagent.enterprise_ai_agent.entity.DocumentStatus;
import com.aiagent.enterprise_ai_agent.entity.User;
import com.aiagent.enterprise_ai_agent.repository.DocumentRepository;
import com.aiagent.enterprise_ai_agent.util.PdfUtil;
import org.springframework.core.io.Resource;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DocumentService {

    private final DocumentRepository documentRepository;

    private final UserService userService;

    private final ConversationService conversationService;

    private final ChunkService chunkService;

    private final VectorStoreService vectorStoreService;

    private final StorageService storageService;

    /**
     * Upload PDF
     */
    public DocumentEntity upload(
            MultipartFile file,
            String conversationId) throws Exception {

        User currentUser = userService.getCurrentUser();

        Conversation conversation =
                conversationService.getConversation(conversationId);

        validateConversationOwner(
                conversation,
                currentUser);

        checkDuplicateFile(
                currentUser,
                file);

        log.info(
                "Uploading file {} for user {}",
                file.getOriginalFilename(),
                currentUser.getEmail());

        String storedFileName =
                storageService.save(file);

        String text =
                PdfUtil.extractText(file);

        List<String> chunks =
                chunkService.split(text);



        DocumentEntity document =
                new DocumentEntity();

        document.setUser(currentUser);
        document.setFileId(UUID.randomUUID().toString());

        document.setConversation(conversation);

        document.setFileName(storedFileName);

        document.setOriginalFileName(
                file.getOriginalFilename());

        document.setContentType(
                file.getContentType());

        document.setFileSize(
                file.getSize());

        document.setChunkCount(
                chunks.size());

        document.setStatus(
                DocumentStatus.READY);

        documentRepository.save(document);
        vectorStoreService.save(

                chunks,

                currentUser.getId(),

                conversation.getConversationId(),

                storedFileName,

                "PDF",
                document.getFileId()

        );

        log.info(
                "Document uploaded successfully {}",
                document.getFileId());

        return document;

    }
    /**
     * Get all documents of current user
     */
    @Transactional(readOnly = true)
    public Page<DocumentEntity> getMyDocuments(Pageable pageable) {

        User currentUser = userService.getCurrentUser();

        return documentRepository.findByUserAndStatusNot(
                currentUser,
                DocumentStatus.DELETED,
                pageable
        );

    }

    /**
     * Get single document by fileId
     */
    @Transactional(readOnly = true)
    public DocumentEntity getDocument(String fileId) {

        User currentUser = userService.getCurrentUser();

        DocumentEntity document = documentRepository
                .findByFileId(fileId)
                .orElseThrow(() ->
                        new RuntimeException("Document not found."));

        validateDocumentOwner(document, currentUser);

        if (document.getStatus() == DocumentStatus.DELETED) {
            throw new RuntimeException("Document has been deleted.");
        }

        return document;

    }

    /**
     * List documents of a conversation
     */
    @Transactional(readOnly = true)
    public List<DocumentEntity> getConversationDocuments(
            String conversationId) {

        User currentUser = userService.getCurrentUser();

        Conversation conversation =
                conversationService.getConversation(conversationId);

        validateConversationOwner(
                conversation,
                currentUser);

        return documentRepository
                .findByConversationAndStatusNotOrderByUploadedAtDesc(
                        conversation,
                        DocumentStatus.DELETED
                );

    }

    /**
     * Soft delete document
     */
    @Transactional
    public void deleteDocument(String fileId) {

        User currentUser =
                userService.getCurrentUser();

        DocumentEntity document =
                documentRepository
                        .findByFileIdAndStatusNot(
                                fileId,
                                DocumentStatus.DELETED)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found"));

        if (!document.getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "Unauthorized access.");

        }

        /*
         * Delete vectors
         */
        vectorStoreService.deleteByFileId(fileId);

        /*
         * Soft delete
         */
        document.setStatus(
                DocumentStatus.DELETED);

        documentRepository.save(document);

        /*
         * Update conversation
         */
        Conversation conversation =
                document.getConversation();

        if (conversation != null) {
            conversation.setDocumentCount(Math.max(0,conversation.getDocumentCount() - 1));

            conversationService.save(conversation);

        }

    }
    /**
     * Validate conversation owner.
     */
    private void validateConversationOwner(
            Conversation conversation,
            User currentUser) {

        if (!conversation.getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You are not authorized to access this conversation.");

        }

    }

    /**
     * Validate document owner.
     */
    private void validateDocumentOwner(
            DocumentEntity document,
            User currentUser) {

        if (!document.getUser()
                .getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You are not authorized to access this document.");

        }

    }

    /**
     * Duplicate file validation.
     */
    private void checkDuplicateFile(
            User currentUser,
            MultipartFile file) {

        boolean exists =
                documentRepository
                        .existsByUserAndOriginalFileNameAndFileSizeAndStatusNot(

                                currentUser,

                                file.getOriginalFilename(),

                                file.getSize(),

                                DocumentStatus.DELETED

                        );

        if (exists) {

            throw new RuntimeException(
                    "Document already uploaded.");

        }

    }
    
    public Resource downloadDocument(String fileId)
            throws Exception {

        User currentUser =
                userService.getCurrentUser();

        DocumentEntity document =
                documentRepository
                        .findByFileIdAndStatusNot(
                                fileId,
                                DocumentStatus.DELETED)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found"));

        validateDocumentOwner(
                document,
                currentUser);

        return storageService.loadAsResource(document.getFileName());

    }

}