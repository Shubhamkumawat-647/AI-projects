package com.aiagent.enterprise_ai_agent.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.aiagent.enterprise_ai_agent.config.DTO.ChatRequest;
import com.aiagent.enterprise_ai_agent.config.DTO.ChatResponse;
import com.aiagent.enterprise_ai_agent.service.AgentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
@Validated
public class ChatController {

    private final AgentService agentService;

//    @PostMapping("/chat/{conversationId}")
//    public ChatResponse chat(
//
//            @PathVariable String conversationId,
//
//            //@RequestBody ChatRequest request
//            ){
//
//        return new ChatResponse(
//
//                agentService.chat(conversationId,
//                        request.getMessage()
//                ), conversationId
//        );
//
    
//    }

}