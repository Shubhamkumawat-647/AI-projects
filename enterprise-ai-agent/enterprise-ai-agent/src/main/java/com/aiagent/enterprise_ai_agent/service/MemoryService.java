package com.aiagent.enterprise_ai_agent.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.Message;
import com.aiagent.enterprise_ai_agent.repository.MessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemoryService {

    private static final int SUMMARY_TRIGGER_COUNT = 20;

    private final ChatClient chatClient;
//    private final MessageService messageService;
    private final ConversationService conversationService;
    private final MessageRepository messageRepository;

    /**
     * Generate or update conversation summary.
     */
    public void updateSummary(Conversation conversation) {

    	List<Message> messages =
    	        messageRepository
    	        .findByConversationOrderByCreatedAtAsc(conversation);

        if (messages.isEmpty()) {
            return;
        }

        StringBuilder history = new StringBuilder();

        for (Message message : messages) {

            history.append(message.getRole().name())
                   .append(": ")
                   .append(message.getContent())
                   .append("\n");

        }

        String prompt = """
                You are an AI memory engine.

                Summarize the following conversation.

                Rules:
                - Keep important facts.
                - Keep user preferences.
                - Keep important decisions.
                - Remove unnecessary greetings.
                - Maximum 250 words.

                Conversation:

                %s
                """.formatted(history);

        String summary =
                chatClient
                        .prompt()
                        .user(prompt)
                        .call()
                        .content();

        conversation.setSummary(summary);

        conversationService.save(conversation);

        log.info(
                "Conversation summary updated : {}",
                conversation.getConversationId());

    }

    /**
     * Returns memory used in PromptService.
     */
    @Transactional(readOnly = true)
    public String getMemory(Conversation conversation) {

        if (conversation.getSummary() == null) {
            return "";
        }

        return conversation.getSummary();
    }

    /**
     * Check whether summary should be regenerated.
     */
    public boolean shouldUpdateSummary(
            Conversation conversation) {

        return conversation.getMessageCount() != null
                && conversation.getMessageCount() > 0
                && conversation.getMessageCount()
                % SUMMARY_TRIGGER_COUNT == 0;
    }

}