package com.ai_interview_assistant.ai.interview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {

    private String documentId;

    private String fileName;

    private String message;

    // Getter Setter

}