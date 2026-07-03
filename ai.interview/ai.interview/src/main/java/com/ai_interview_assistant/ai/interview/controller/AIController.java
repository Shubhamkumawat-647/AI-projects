package com.ai_interview_assistant.ai.interview.controller;

import com.ai_interview_assistant.ai.interview.dto.ChatRequest;
import com.ai_interview_assistant.ai.interview.dto.ChatResponse;
import com.ai_interview_assistant.ai.interview.service.AIService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {

        String response = aiService.askAI(request.getMessage());

        return new ChatResponse(response);

    }

}