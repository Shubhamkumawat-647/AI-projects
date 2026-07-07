package com.ai_interview_assistant.ai.interview.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_interview_assistant.ai.interview.entity.DocumentEntity;
import com.ai_interview_assistant.ai.interview.service.DocumentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@AllArgsConstructor
public class DocumentController {

	private final DocumentService documentService;

	@GetMapping
	public List<DocumentEntity> getAll() {
		return documentService.getAllDocuments();
	}
	@GetMapping("/{documentId}")
	public DocumentEntity getDocument(@PathVariable String documentId) {
		return documentService.getDocument(documentId);
	}
	@DeleteMapping("/{documentId}")
	public String delete(@PathVariable String documentId) {
		documentService.deleteDocument(documentId);
		return "Document Deleted";

	}

}