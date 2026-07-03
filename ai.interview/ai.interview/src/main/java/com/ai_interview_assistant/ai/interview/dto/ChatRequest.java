package com.ai_interview_assistant.ai.interview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatRequest {

    @NotBlank(message = "Message cannot be blank")
    private String message;
}