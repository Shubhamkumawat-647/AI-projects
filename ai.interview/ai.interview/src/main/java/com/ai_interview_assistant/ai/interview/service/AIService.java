package com.ai_interview_assistant.ai.interview.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.Message;

import com.ai_interview_assistant.ai.interview.config.PromptConfig;
import com.ai_interview_assistant.ai.interview.entity.ChatMessage;
import com.ai_interview_assistant.ai.interview.entity.ChatSession;
import com.ai_interview_assistant.ai.interview.entity.MessageRole;
import com.ai_interview_assistant.ai.interview.repository.ChatMessageRepository;
import com.ai_interview_assistant.ai.interview.repository.ChatSessionRepository;

@Service
public class AIService {

	private final ChatModel chatModel;

    private final ChatSessionRepository chatSessionRepository;

    private final ChatMessageRepository chatMessageRepository;

    public AIService(ChatModel chatModel,
                     ChatSessionRepository chatSessionRepository,
                     ChatMessageRepository chatMessageRepository) {

    	 this.chatModel = chatModel;
        this.chatSessionRepository = chatSessionRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public String askAI(String conversationId,String userMessage) {
    	
    	ChatSession session =chatSessionRepository.findByConversationId(conversationId).orElseThrow(() ->new RuntimeException("Conversation not found"));

    	List<ChatMessage> history =chatMessageRepository.findByChatSessionOrderByCreatedAtAsc(session);
       
    	List<Message> messages = new ArrayList<>();

//    	messages.add(new SystemMessage(PromptConfig.SYSTEM_PROMPT));
    	
    	for(ChatMessage chat : history){
    	    if(chat.getRole()==MessageRole.USER){
    	        messages.add(new UserMessage(chat.getMessage()));
    	    }
    	    else{
    	    	messages.add(new AssistantMessage(chat.getMessage()));
    	    }
    	}
    	messages.add(new UserMessage(userMessage));
    	
    	Prompt prompt =new Prompt(messages);
    	
    	 String response =chatModel.call(prompt).getResult().getOutput().getText();
//    	 String response = chatClient
//    		        .prompt(prompt)
//    		        .call()
//    		        .content();

        ChatMessage userChat = new ChatMessage();
        userChat.setRole(MessageRole.USER);
        userChat.setMessage(userMessage);
        userChat.setCreatedAt(LocalDateTime.now());
        userChat.setChatSession(session);
        chatMessageRepository.save(userChat);
        

        ChatMessage aiChat = new ChatMessage();
        aiChat.setRole(MessageRole.ASSISTANT);
        aiChat.setMessage(response);
        aiChat.setCreatedAt(LocalDateTime.now());
        aiChat.setChatSession(session);
        chatMessageRepository.save(aiChat);
        return response;

    }

}