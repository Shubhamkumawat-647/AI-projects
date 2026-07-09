package com.aiagent.enterprise_ai_agent.config.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenameConversationRequest {

    @NotBlank(message = "Title is required")
    private String title;

}