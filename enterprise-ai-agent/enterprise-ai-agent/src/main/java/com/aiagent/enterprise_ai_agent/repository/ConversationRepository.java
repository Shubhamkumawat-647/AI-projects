package com.aiagent.enterprise_ai_agent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.User;

public interface ConversationRepository
        extends JpaRepository<Conversation, Long>{

    Optional<Conversation>
    findByConversationId(String conversationId);
    
    Optional<Conversation> findByConversationIdAndUserId(String conversationId,Long userId);
    
    Optional<Conversation> findByConversationIdAndUser(
            String conversationId,
            User user);

    List<Conversation> findByUserOrderByCreatedAtDesc(
            User user);
    
    Page<Conversation> findByUserAndDeletedFalseOrderByLastActiveAtDesc(
            User user,
            Pageable pageable);

    Optional<Conversation> findByConversationIdAndDeletedFalse(
            String conversationId);

    Page<Conversation> findByUserAndTitleContainingIgnoreCaseAndDeletedFalse(
            User user,
            String keyword,
            Pageable pageable
    );

}

