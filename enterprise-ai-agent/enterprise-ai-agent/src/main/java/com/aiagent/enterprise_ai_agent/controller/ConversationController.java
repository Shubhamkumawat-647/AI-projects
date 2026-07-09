package com.aiagent.enterprise_ai_agent.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.User;
import com.aiagent.enterprise_ai_agent.service.ConversationService;
import com.aiagent.enterprise_ai_agent.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    private final UserService userService;

    /**
     * Get all conversations of logged-in user
     *
     * GET /api/conversations?page=0&size=20
     */
    @GetMapping
    public Page<Conversation> getMyConversations(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size) {

        User currentUser = userService.getCurrentUser();

        return conversationService.getMyConversations(
                currentUser,
                page,
                size);

    }

    /**
     * Search conversation by title
     *
     * GET /api/conversations/search?keyword=spring
     */
    @GetMapping("/search")
    public Page<Conversation> searchConversation(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "20")
            int size) {

        User currentUser = userService.getCurrentUser();

        return conversationService.searchConversation(
                currentUser,
                keyword,
                page,
                size);

    }

    /**
     * Rename conversation
     *
     * PUT /api/conversations/{conversationId}/title
     */
    @PutMapping("/{conversationId}/title")
    public Conversation renameConversation(

            @PathVariable String conversationId,

            @RequestBody Map<String, String> request) {

        User currentUser = userService.getCurrentUser();

        return conversationService.renameConversation(

                conversationId,

                request.get("title"),

                currentUser);

    }

    /**
     * Soft delete conversation
     *
     * DELETE /api/conversations/{conversationId}
     */
    @DeleteMapping("/{conversationId}")
    public String deleteConversation(

            @PathVariable String conversationId) {

        User currentUser = userService.getCurrentUser();

        conversationService.deleteConversation(
                conversationId,
                currentUser);

        return "Conversation deleted successfully.";

    }

    /**
     * Restore deleted conversation
     *
     * PUT /api/conversations/{conversationId}/restore
     */
    @PutMapping("/{conversationId}/restore")
    public Conversation restoreConversation(

            @PathVariable String conversationId) {

        User currentUser = userService.getCurrentUser();

        return conversationService.restoreConversation(
                conversationId,
                currentUser);

    }

}