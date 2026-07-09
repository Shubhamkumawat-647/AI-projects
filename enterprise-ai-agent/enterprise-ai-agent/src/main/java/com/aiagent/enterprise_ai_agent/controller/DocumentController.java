package com.aiagent.enterprise_ai_agent.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.aiagent.enterprise_ai_agent.entity.DocumentEntity;
import com.aiagent.enterprise_ai_agent.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    /**
     * Upload Document
     */
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentEntity upload(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam("conversationId")
            String conversationId

    ) throws Exception {

        return documentService.upload(
                file,
                conversationId
        );

    }

    /**
     * My Documents
     */
    @GetMapping
    public Page<DocumentEntity> getMyDocuments(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size

    ) {

        return documentService.getMyDocuments(

                PageRequest.of(page, size)

        );

    }

    /**
     * Conversation Documents
     */
    @GetMapping("/conversation/{conversationId}")
    public java.util.List<DocumentEntity> getConversationDocuments(

            @PathVariable
            String conversationId

    ) {

        return documentService
                .getConversationDocuments(
                        conversationId
                );

    }

    /**
     * Single Document
     */
    @GetMapping("/{fileId}")
    public DocumentEntity getDocument(

            @PathVariable
            String fileId

    ) {

        return documentService.getDocument(
                fileId
        );

    }

    /**
     * Delete Document
     */
    @DeleteMapping("/{fileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(

            @PathVariable
            String fileId

    ) throws Exception {

        documentService.deleteDocument(
                fileId
        );

    }

}