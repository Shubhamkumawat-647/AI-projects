package com.ai_interview_assistant.ai.interview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.Message;

import com.ai_interview_assistant.ai.interview.config.PromptConfig;

@Service
public class AIService {

	private final ChatClient chatClient;

    public AIService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String askAI(String userMessage) {

        return chatClient
                .prompt()
                .system(PromptConfig.SYSTEM_PROMPT)
                .user(userMessage)
                .call()
                .content();

    }
}