package com.ai_interview_assistant.ai.interview.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_interview_assistant.ai.interview.dto.RagRequest;
import com.ai_interview_assistant.ai.interview.dto.RagResponse;
import com.ai_interview_assistant.ai.interview.entity.DocumentEntity;
import com.ai_interview_assistant.ai.interview.service.AIService;
import com.ai_interview_assistant.ai.interview.service.DocumentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@AllArgsConstructor
public class DocumentController {

	private final DocumentService documentService;
	private final AIService aiService;

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
	@PostMapping("/document1")
	public RagResponse askDocument(
	        @RequestBody RagRequest request){
		System.out.println(request.getQuestion());
	    return aiService.askFromDocument(request);

	}

}