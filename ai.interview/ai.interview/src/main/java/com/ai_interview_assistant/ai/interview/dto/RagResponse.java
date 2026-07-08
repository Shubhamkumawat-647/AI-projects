package com.ai_interview_assistant.ai.interview.dto;

import java.util.List;

import lombok.Data;

@Data
public class RagResponse {

    /**
     * AI Generated Answer
     */
    private String answer;

    /**
     * Source Documents
     */
    private List<String> sources;

    /**
     * Number of Retrieved Chunks
     */
    private int retrievedChunks;

    /**
     * Response Time in Milliseconds
     */
    private long responseTime;

}