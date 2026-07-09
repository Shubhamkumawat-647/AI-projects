package com.aiagent.enterprise_ai_agent.config.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RagResponse {

    private String answer;

    private List<String> sources;

}