package com.aiagent.enterprise_ai_agent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aiagent.enterprise_ai_agent.config.PromptConfig;
import com.aiagent.enterprise_ai_agent.entity.Message;

@Service
public class PromptService {

    /**
     * Build final prompt for LLM
     */
    public String buildPrompt(
            List<Message> history,
            String ragContext,
            String userMessage) {

        StringBuilder prompt = new StringBuilder();

        // System Prompt
        prompt.append(PromptConfig.SYSTEM_PROMPT)
              .append("\n\n");

        // Knowledge Base
        if (ragContext != null && !ragContext.isBlank()) {

            prompt.append("========== KNOWLEDGE BASE ==========\n");
            prompt.append(ragContext);
            prompt.append("\n\n");

        }

        // Chat History
        if (history != null && !history.isEmpty()) {

            prompt.append("========== CONVERSATION HISTORY ==========\n");

            for (Message message : history) {

                prompt.append(message.getRole().name())
                        .append(": ")
                        .append(message.getContent())
                        .append("\n");

            }

            prompt.append("\n");

        }

        // Current Question
        prompt.append("========== CURRENT USER QUESTION ==========\n");
        prompt.append(userMessage);

        return prompt.toString();
    }

}