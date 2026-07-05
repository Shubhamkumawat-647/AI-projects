package com.ai_interview_assistant.ai.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai_interview_assistant.ai.interview.entity.ChatMessage;
import com.ai_interview_assistant.ai.interview.entity.ChatSession;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, Long> {
	
	List<ChatMessage> findByChatSessionOrderByCreatedAtAsc(ChatSession chatSession);

}