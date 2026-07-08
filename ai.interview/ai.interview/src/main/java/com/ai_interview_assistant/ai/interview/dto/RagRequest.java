package com.ai_interview_assistant.ai.interview.dto;

import lombok.Data;

@Data
public class RagRequest {

    // User question
    private String question;

    // PDF file name stored in Vector Store metadata
    private String fileName;

    // Conversation ID (Day 61/62)
    private String conversationId;

}