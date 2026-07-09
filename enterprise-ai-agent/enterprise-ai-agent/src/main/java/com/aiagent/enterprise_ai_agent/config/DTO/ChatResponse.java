package com.aiagent.enterprise_ai_agent.config.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private String response;
    private String conversationId;
    private String title;
    private List<String> sources;
}