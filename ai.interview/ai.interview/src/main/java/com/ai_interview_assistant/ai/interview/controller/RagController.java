package com.ai_interview_assistant.ai.interview.controller;

import com.ai_interview_assistant.ai.interview.dto.RagRequest;
import com.ai_interview_assistant.ai.interview.service.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody RagRequest request) {

        return ragService.askQuestion(
                request.getQuestion(),request.getFileName());

    }

}