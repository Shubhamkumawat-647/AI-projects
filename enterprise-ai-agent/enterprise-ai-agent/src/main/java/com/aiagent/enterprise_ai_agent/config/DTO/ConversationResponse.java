package com.aiagent.enterprise_ai_agent.config.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ConversationResponse {

    private String conversationId;
    private String title;
    private LocalDateTime createdAt;

}