package com.ai_interview_assistant.ai.interview.controller;

import org.springframework.web.bind.annotation.*;

import com.ai_interview_assistant.ai.interview.dto.AnswerEvaluationRequest;
import com.ai_interview_assistant.ai.interview.dto.AnswerEvaluationResponse;
import com.ai_interview_assistant.ai.interview.service.AnswerEvaluationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/evaluation")
@AllArgsConstructor
public class AnswerEvaluationController {

    private final AnswerEvaluationService evaluationService;

    @PostMapping
    public AnswerEvaluationResponse evaluate(
            @RequestBody AnswerEvaluationRequest request)
            throws Exception {

        return evaluationService.evaluate(
                request.getQuestion(),
                request.getAnswer());
    }
}