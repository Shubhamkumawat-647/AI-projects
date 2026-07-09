package com.aiagent.enterprise_ai_agent.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.Message;
import com.aiagent.enterprise_ai_agent.entity.Role;
import com.aiagent.enterprise_ai_agent.repository.ConversationRepository;
import com.aiagent.enterprise_ai_agent.repository.MessageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
//    private final MemoryService memoryService;

    /**
     * Save User Message
     */
    public Message saveUserMessage(
            Conversation conversation,
            String content) {

        Message message = new Message();

        message.setConversation(conversation);
        message.setRole(Role.USER);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    /**
     * Save Assistant Message
     */
    public Message saveAssistantMessage(
            Conversation conversation,
            String content) {

        Message message = new Message();

        message.setConversation(conversation);
        message.setRole(Role.ASSISTANT);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    /**
     * Load Complete Chat History
     */
    public List<Message> getConversationHistory(
            Conversation conversation) {

        return messageRepository
                .findByConversationOrderByCreatedAtAsc(conversation);
    }

    /**
     * Load Last 20 Messages
     */
    public List<Message> getRecentHistory(
            Conversation conversation) {

        return messageRepository
                .findTop20ByConversationOrderByCreatedAtDesc(conversation);
    }
    
    public Message save(
            Conversation conversation,
            Role role,
            String content) {

        Message message = new Message();

        message.setConversation(conversation);
        message.setRole(role);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        conversation.setMessageCount(conversation.getMessageCount() + 1);

        conversationRepository.save(conversation);
        if (conversation.getMessageCount() % 20 == 0) {

//            memoryService.updateSummary(conversation);

        }

        return savedMessage;
    }

    /**
     * Last 20 messages
     */
    @Transactional
    public List<Message> getRecentMessages(Conversation conversation) {
        return messageRepository.findTop20ByConversationOrderByCreatedAtDesc(conversation);
    }

    /**
     * Complete chat history
     */
    @Transactional
    public List<Message> getAllMessages(
            Conversation conversation) {

        return messageRepository
                .findByConversationOrderByCreatedAtAsc(
                        conversation);
    }

}