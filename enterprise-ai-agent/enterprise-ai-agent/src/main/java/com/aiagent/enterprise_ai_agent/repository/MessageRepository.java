package com.aiagent.enterprise_ai_agent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

	List<Message> findByConversationOrderByCreatedAtAsc(Conversation conversation);
	List<Message>findTop20ByConversationOrderByCreatedAtDesc(Conversation conversation);
	


}