package com.ai_interview_assistant.ai.interview.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnswerEvaluationResponse {

    private Integer score;

    private List<String> strengths;

    private List<String> weaknesses;

    private String correctAnswer;

    private List<String> followUpQuestions;

    private String feedback;

}