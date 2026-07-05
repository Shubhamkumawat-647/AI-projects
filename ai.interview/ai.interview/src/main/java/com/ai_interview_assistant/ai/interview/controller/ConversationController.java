package com.ai_interview_assistant.ai.interview.controller;

import org.springframework.web.bind.annotation.*;

import com.ai_interview_assistant.ai.interview.dto.ConversationResponse;
import com.ai_interview_assistant.ai.interview.service.ConversationService;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService service;

    public ConversationController(ConversationService service) {

        this.service = service;

    }

    @PostMapping
    public ConversationResponse createConversation() {

        return new ConversationResponse(

                service.createConversation()

        );

    }

}