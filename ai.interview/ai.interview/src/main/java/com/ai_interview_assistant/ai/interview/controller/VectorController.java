package com.ai_interview_assistant.ai.interview.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_interview_assistant.ai.interview.service.VectorStoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VectorController {

    private final VectorStoreService vectorStoreService;

    @GetMapping("/vector/load")
    public String loadVector() {

        vectorStoreService.saveText(
                "Spring Boot is an enterprise Java framework.");

        return "Embedding Stored Successfully";

    }
    @GetMapping("/vector/load-all")
    public String loadAll() {

        vectorStoreService.saveDocuments();

        return "All Documents Stored";

    }

}