package com.aiagent.enterprise_ai_agent.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aiagent.enterprise_ai_agent.config.DTO.ApiResponse;
import com.aiagent.enterprise_ai_agent.config.DTO.ConversationResponse;
import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.User;
import com.aiagent.enterprise_ai_agent.repository.ConversationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationService {

	private final ConversationRepository repository;

//    Create a new conversation
	public String createConversation() {
		Conversation conversation = new Conversation();
		conversation.setConversationId(UUID.randomUUID().toString());
		conversation.setCreatedAt(LocalDateTime.now());
		conversation.setTitle(null);
		repository.save(conversation);
		return conversation.getConversationId();
	}

	public List<ConversationResponse> getAll() {
		return repository.findAll().stream().map(this::map).toList();
	}

	private ConversationResponse map(Conversation conversation) {
		ConversationResponse dto = new ConversationResponse();
		dto.setConversationId(conversation.getConversationId());
		dto.setCreatedAt(conversation.getCreatedAt());
		dto.setTitle(conversation.getTitle());
		return dto;
	}

	public void renameConversation(String conversationId, String title) {

		Conversation conversation = repository.findByConversationId(conversationId)
				.orElseThrow(() -> new RuntimeException("Conversation not found"));

		conversation.setTitle(title.trim());

		repository.save(conversation);

	}

	public void deleteConversation(String conversationId) {

		Conversation conversation = repository.findByConversationId(conversationId)
				.orElseThrow(() -> new RuntimeException("Conversation not found."));

		repository.delete(conversation);

	}

//__________________________________________________________________________________

	    /**
	     * Create new conversation
	     */
	    public Conversation createConversation(User user) {

	        Conversation conversation = new Conversation();

	        conversation.setConversationId(UUID.randomUUID().toString());
	        conversation.setUser(user);
	        conversation.setTitle(null);
	        conversation.setDeleted(false);
	        conversation.setMessageCount(0);
	        conversation.setSummary(null);

	        return repository.save(conversation);
	    }

	    /**
	     * Find conversation by public conversationId
	     */
	    @Transactional
	    public Conversation getConversation(String conversationId) {

	        return repository
	                .findByConversationIdAndDeletedFalse(conversationId)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Conversation not found : "
	                                        + conversationId));
	    }

	    /**
	     * Existing conversation OR create new
	     */
	    
	    public ApiResponse<Conversation> getOrCreateConversation(
	            String conversationId,
	            User user) {

	        Conversation conversation;

	        if (conversationId == null || conversationId.isBlank()) {
	            conversation = createConversation(user);
	        } else {
	            conversation = getConversation(conversationId);
	            validateOwner(conversation, user);
	        }

	        return ApiResponse.<Conversation>builder()
	                .success(true)
	                .message("Conversation fetched successfully")
	                .data(conversation)
	                .timestamp(LocalDateTime.now())
	                .build();
	    }

	    /**
	     * Verify conversation owner
	     */
	    public void validateOwner(
	            Conversation conversation,
	            User user) {

	        if (!conversation.getUser()
	                .getId()
	                .equals(user.getId())) {

	            throw new RuntimeException(
	                    "Unauthorized conversation access.");

	        }

	    }

	    /**
	     * Update title
	     */
	    public Conversation renameConversation(
	            String conversationId,
	            String title,
	            User user) {

	        Conversation conversation =
	                getConversation(conversationId);

	        validateOwner(conversation, user);

	        conversation.setTitle(title);

	        return repository.save(conversation);
	    }

	    /**
	     * Soft delete
	     */
	    public void deleteConversation(
	            String conversationId,
	            User user) {

	        Conversation conversation =
	                getConversation(conversationId);

	        validateOwner(conversation, user);

	        conversation.setDeleted(true);

	        repository.save(conversation);

	    }

	    /**
	     * Restore conversation
	     */
	    public Conversation restoreConversation(
	            String conversationId,
	            User user) {

	        Conversation conversation =
	                repository.findByConversationId(conversationId)
	                        .orElseThrow(() ->
	                                new RuntimeException(
	                                        "Conversation not found"));

	        validateOwner(conversation, user);

	        conversation.setDeleted(false);

	        return repository.save(conversation);
	    }

	    /**
	     * User conversations
	     */
	    @Transactional
	    public Page<Conversation> getMyConversations(
	            User user,
	            int page,
	            int size) {

	        return repository
	                .findByUserAndDeletedFalseOrderByLastActiveAtDesc(
	                        user,
	                        PageRequest.of(page, size));

	    }

	    /**
	     * Search conversation
	     */
	    @Transactional
	    public Page<Conversation> searchConversation(
	            User user,
	            String keyword,
	            int page,
	            int size) {

	        return repository
	                .findByUserAndTitleContainingIgnoreCaseAndDeletedFalse(
	                        user,
	                        keyword, PageRequest.of(page, size));

	    }

	    /**
	     * Update last activity
	     */
	    public void touch(Conversation conversation) {

	        conversation.setLastActiveAt(
	                LocalDateTime.now());

	        repository.save(conversation);

	    }

	    /**
	     * Save conversation
	     */
	    public Conversation save(
	            Conversation conversation) {

	        return repository.save(conversation);

	    }

}