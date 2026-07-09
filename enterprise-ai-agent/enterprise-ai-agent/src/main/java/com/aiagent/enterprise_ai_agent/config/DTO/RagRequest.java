package com.aiagent.enterprise_ai_agent.config.DTO;

import lombok.Data;

@Data
public class RagRequest {

    private String question;
    private Long userId;

}