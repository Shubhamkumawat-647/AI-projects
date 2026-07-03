package com.ai_interview_assistant.ai.interview.service;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.ai_interview_assistant.ai.interview.config.PromptConfig;

@Service
public class AIService {

    private final ChatModel chatModel;

    public AIService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String askAI(String userMessage) {
    	
    	Prompt prompt = new Prompt(
    			new SystemMessage(PromptConfig.SYSTEM_PROMPT),
    			new UserMessage(userMessage));

        return chatModel.call(prompt).getResult().getOutput().getText();

    }

}