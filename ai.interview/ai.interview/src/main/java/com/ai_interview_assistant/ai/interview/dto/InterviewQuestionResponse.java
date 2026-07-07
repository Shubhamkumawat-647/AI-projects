package com.ai_interview_assistant.ai.interview.dto;

import lombok.Data;
import java.util.List;

@Data
public class InterviewQuestionResponse {

    private List<String> javaQuestions;

    private List<String> springBootQuestions;

    private List<String> microserviceQuestions;

    private List<String> dockerQuestions;

    private List<String> projectQuestions;

}