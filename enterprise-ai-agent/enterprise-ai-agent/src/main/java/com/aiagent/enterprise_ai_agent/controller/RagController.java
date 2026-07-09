package com.aiagent.enterprise_ai_agent.controller;

import org.springframework.web.bind.annotation.*;

import com.aiagent.enterprise_ai_agent.config.DTO.RagRequest;
import com.aiagent.enterprise_ai_agent.config.DTO.RagResponse;
import com.aiagent.enterprise_ai_agent.service.RagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;

    @PostMapping("/ask")
    public RagResponse ask(
            @RequestBody RagRequest request){

        return ragService.ask(
                request.getQuestion(),request.getUserId());

    }

}