package com.ai_interview_assistant.ai.interview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai_interview_assistant.ai.interview.entity.ChatSession;

public interface ChatSessionRepository
        extends JpaRepository<ChatSession, Long> {

    Optional<ChatSession> findByConversationId(String conversationId);

}