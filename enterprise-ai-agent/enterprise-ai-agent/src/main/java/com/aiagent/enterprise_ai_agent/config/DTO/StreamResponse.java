package com.aiagent.enterprise_ai_agent.config.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamResponse {

    private String type;

    private String content;

}