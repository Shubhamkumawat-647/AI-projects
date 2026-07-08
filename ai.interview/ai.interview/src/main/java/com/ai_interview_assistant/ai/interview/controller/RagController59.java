package com.ai_interview_assistant.ai.interview.controller;



import org.springframework.web.bind.annotation.*;



import com.ai_interview_assistant.ai.interview.dto.RagRequest;
import com.ai_interview_assistant.ai.interview.dto.RagResponse;
import com.ai_interview_assistant.ai.interview.service.RagService;
import com.ai_interview_assistant.ai.interview.service.RagService59;

import lombok.RequiredArgsConstructor;



@RestController

@RequestMapping("/api/rag")

@RequiredArgsConstructor

public class RagController59 { 
    private final RagService59 ragService; 
    @PostMapping("/ask1")
    public RagResponse ask(@RequestBody RagRequest request) {

        return ragService.ask(request);

    }
}