package com.ai_interview_assistant.ai.interview.controller;

import com.ai_interview_assistant.ai.interview.dto.ChatRequest;
import com.ai_interview_assistant.ai.interview.dto.ChatResponse;
import com.ai_interview_assistant.ai.interview.service.AIService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversations")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/{conversationId}/messages")
    public String chat(
            @PathVariable String conversationId,
            @Valid @RequestBody ChatRequest request){

        return aiService.askAI(
                conversationId,
                request.getMessage());

    }

}