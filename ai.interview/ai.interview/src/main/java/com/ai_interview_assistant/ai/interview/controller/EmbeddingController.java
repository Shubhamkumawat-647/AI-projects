package com.ai_interview_assistant.ai.interview.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ai_interview_assistant.ai.interview.service.EmbeddingService;

@RestController
@RequestMapping("/api/embedding")
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @GetMapping
    public float[] generateEmbedding(
            @RequestParam String text) {

    	
        return embeddingService.generateEmbedding(text);

    }

}