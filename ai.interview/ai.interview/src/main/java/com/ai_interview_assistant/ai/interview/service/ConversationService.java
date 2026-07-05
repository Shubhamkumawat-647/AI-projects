package com.ai_interview_assistant.ai.interview.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ai_interview_assistant.ai.interview.entity.ChatSession;
import com.ai_interview_assistant.ai.interview.repository.ChatSessionRepository;

@Service
public class ConversationService {

    private final ChatSessionRepository repository;

    public ConversationService(ChatSessionRepository repository) {

        this.repository = repository;

    }

    public String createConversation() {

        ChatSession session = new ChatSession();

        session.setConversationId(
                UUID.randomUUID().toString()
        );

        session.setCreatedAt(
                LocalDateTime.now()
        );

        repository.save(session);

        return session.getConversationId();

    }

}