//package com.aiagent.enterprise_ai_agent.service;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.stereotype.Service;
//
//import com.aiagent.enterprise_ai_agent.config.PromptConfig;
//import com.aiagent.enterprise_ai_agent.entity.Conversation;
//import com.aiagent.enterprise_ai_agent.entity.Message;
//import com.aiagent.enterprise_ai_agent.entity.Role;
//import com.aiagent.enterprise_ai_agent.repository.ConversationRepository;
//import com.aiagent.enterprise_ai_agent.repository.MessageRepository;
//import com.aiagent.enterprise_ai_agent.tools.CalculatorTool;
////import com.aiagent.enterprise_ai_agent.tools.EmailTool;
//import com.aiagent.enterprise_ai_agent.tools.WeatherTool;
//
//import lombok.RequiredArgsConstructor;
//import reactor.core.publisher.Flux;
//
//@Service
//@RequiredArgsConstructor
//public class AgentService {
//
//    private final ChatClient chatClient;
//    private final CalculatorTool calculatorTool;
//    private final WeatherTool weatherTool;
////    private final EmailTool emailTool;
//
//    private final ConversationRepository conversationRepository;
//
//    private final MessageRepository messageRepository;
//    
////    public String chat(String message){
////        return chatClient
////
////                .prompt()
////
////                .system(PromptConfig.SYSTEM_PROMPT)
////
////                .user(message)
////                .tools(calculatorTool,
////                        weatherTool)
////
////                .call()
////
////                .content();
////
////    }
//public String chat(String conversationId, String userMessage) {
//		
//		Conversation conversation = conversationRepository.findByConversationId(conversationId)
//				.orElseThrow(() -> new RuntimeException("Conversation not found"));
//		
//		// Load previous messages
//        List<Message> history = messageRepository
//                .findTop20ByConversationOrderByCreatedAtDesc(conversation);
//        if(conversation.getTitle()==null){
//        	generateConversationTitle(conversation,userMessage);
//        	}
//     // Build prompt with history
//        Collections.reverse(history);
//        StringBuilder prompt = new StringBuilder();
//        prompt.append(PromptConfig.SYSTEM_PROMPT);
//        prompt.append("\n\n");
//        prompt.append("Conversation History:\n\n");
//
//        for (Message message : history) {
//
//            prompt.append(message.getRole().name())
//                    .append(": ")
//                    .append(message.getContent())
//                    .append("\n");
//
//        }
//
//        prompt.append("\n");
//        prompt.append("Current User Message:\n");
//        prompt.append(userMessage);
//
//        // Call AI
//        String aiResponse = chatClient
//                .prompt()
//                .user(prompt.toString())
//                .tools(calculatorTool,
//                        weatherTool)
//                .call()
//                .content();
//        
//		Message user = new Message();
//		user.setConversation(conversation);
//		user.setRole(Role.USER);
//		user.setContent(userMessage);
//		user.setCreatedAt(LocalDateTime.now());
//		
//		messageRepository.save(user);
//		return aiResponse;
//	}
//private void generateConversationTitle(
//        Conversation conversation,
//        String firstMessage) {
//
//    if (conversation.getTitle() != null) {
//        return;
//    }
//    String title = chatClient
//            .prompt()
//            .user(PromptConfig.TITLE_PROMPT
//                            .formatted(firstMessage)
//            )
//            .call()
//            .content();
//    conversation.setTitle(title.trim());
//    conversationRepository.save(conversation);
//}
//public Flux<String> streamChat(String conversationId,String userMessage){
//	Conversation conversation =conversationRepository.findByConversationId(conversationId)
//			.orElseThrow(()->new RuntimeException("Conversation not found"));
//	
//	Message user = new Message();
//	user.setConversation(conversation);
//	user.setRole(Role.USER);
//	user.setContent(userMessage);
//	user.setCreatedAt(LocalDateTime.now());
//	messageRepository.save(user);
//	
//	StringBuilder fullResponse =new StringBuilder();
//	Flux<String> stream = chatClient.prompt()
//	        .system(PromptConfig.SYSTEM_PROMPT)
//	        .user(userMessage)
//	        .tools(calculatorTool,
//	                weatherTool
//	        )
//	        .stream()
//	        .content();
//	
//	return stream.doOnNext(token -> {
//			    fullResponse.append(token);
//			}).doOnComplete(() -> {
//
//	            Message assistant = new Message();
//	            assistant.setConversation(conversation);
//	            assistant.setRole(Role.ASSISTANT);
//	            assistant.setContent(fullResponse.toString());
//	            assistant.setCreatedAt(LocalDateTime.now());
//
//	            messageRepository.save(assistant);
//
//	        });
//
//}
//
//}
//_________________________________________________________________________
//package com.aiagent.enterprise_ai_agent.service;
//
//import java.util.List;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.stereotype.Service;
//
//import com.aiagent.enterprise_ai_agent.config.DTO.ChatRequest;
//import com.aiagent.enterprise_ai_agent.config.DTO.ChatResponse;
//import com.aiagent.enterprise_ai_agent.entity.Conversation;
//import com.aiagent.enterprise_ai_agent.entity.Message;
//import com.aiagent.enterprise_ai_agent.entity.User;
//import com.aiagent.enterprise_ai_agent.tools.CalculatorTool;
//import com.aiagent.enterprise_ai_agent.tools.WeatherTool;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class AgentService {
//
//    private final ChatClient chatClient;
//    private final CalculatorTool calculatorTool;
//    private final WeatherTool weatherTool;
//    private final ConversationService conversationService;
//    private final MessageService messageService;
//    private final TitleService titleService;
//    private final UserService userService;
//
//    /**
//     * Main Chat Method
//     */
//    public ChatResponse chat(ChatRequest request) {
//        // Step 1 : Create or Load Conversation
//    	User currentUser = userService.getCurrentUser();
//        Conversation conversation = conversationService.getOrCreateConversation(request.getConversationId(),currentUser);
//
//        // Step 2 : Save User Message
//        messageService.saveUserMessage(conversation,request.getMessage());
//        // Step 3 : Generate Conversation Title
//        titleService.generateTitle(conversation,request.getMessage());
//
//        // Step 4 : Load Chat History
//        List<Message> history =messageService.getRecentHistory(conversation);
//
//        // Step 5 : Build Prompt
//        String prompt = buildPrompt(history,request.getMessage());
//
//        // Step 6 : AI Response
//        String aiResponse = generateAIResponse(prompt);
//
//        // Step 7 : Save Assistant Message
//        messageService.saveAssistantMessage(conversation,aiResponse);
//
//        // Step 8 : Return Response
//        ChatResponse response = new ChatResponse();
//        response.setConversationId(conversation.getConversationId());response.setTitle(conversation.getTitle());
//        response.setResponse(aiResponse);
//        return response;
//    }
//
//    /**
//     * Build Complete Prompt
//     */
//    private String buildPrompt(
//            List<Message> history,
//            String currentMessage) {
//    	
//        StringBuilder prompt = new StringBuilder();
//        // System Prompt
//        prompt.append("""
//                ============================SYSTEM
//                ============================
//                """);
//
//        prompt.append(com.aiagent.enterprise_ai_agent.config.PromptConfig.SYSTEM_PROMPT);
//        prompt.append("\n\n");
//        // Conversation History
//        if (!history.isEmpty()) {
//
//            prompt.append("""
//                    ============================
//                    CONVERSATION HISTORY
//                    ============================
//
//                    """);
//
//            for (Message message : history) {
//
//                prompt.append(message.getRole().name())
//                        .append(" : ")
//                        .append(message.getContent())
//                        .append("\n");
//
//            }
//
//            prompt.append("\n");
//        }
//
//        // Current User Message
//        prompt.append("""
//                ============================
//                CURRENT USER MESSAGE
//                ============================
//
//                """);
//
//        prompt.append(currentMessage);
//
//        return prompt.toString();
//    }
//
//    /**
//     * Call Ollama
//     */
//    private String generateAIResponse(
//            String prompt) {
//
//        String response = chatClient
//
//                .prompt()
//
//                .user(prompt)
//
//                .tools(
//                        calculatorTool,
//                        weatherTool)
//
//                .call()
//
//                .content();
//
//        if (response == null || response.isBlank()) {
//            return "Sorry, I couldn't generate a response.";
//        }
//
//        return response.trim();
//    }
//
//}

//____________________________________________________________________

//_________________________________________________________________________
//package com.aiagent.enterprise_ai_agent.service;
//
//import java.util.List;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.stereotype.Service;
//
//import com.aiagent.enterprise_ai_agent.config.DTO.ChatRequest;
//import com.aiagent.enterprise_ai_agent.config.DTO.ChatResponse;
//import com.aiagent.enterprise_ai_agent.entity.Conversation;
//import com.aiagent.enterprise_ai_agent.entity.Message;
//import com.aiagent.enterprise_ai_agent.entity.User;
//import com.aiagent.enterprise_ai_agent.tools.CalculatorTool;
//import com.aiagent.enterprise_ai_agent.tools.WeatherTool;
//
//import lombok.RequiredArgsConstructor;
//import reactor.core.publisher.Flux;
//
//@Service
//@RequiredArgsConstructor
//public class AgentService {
//
//    private final ChatClient chatClient;
//
//    private final UserService userService;
//
//    private final ConversationService conversationService;
//
//    private final MessageService messageService;
//
//    private final TitleService titleService;
//
//    private final RagService ragService;
//
//    private final CalculatorTool calculatorTool;
//
//    private final WeatherTool weatherTool;
//
//    public ChatResponse chat(ChatRequest request) {
//
//        // Logged-in User
//        User currentUser = userService.getCurrentUser();
//
//        // Create existing conversation or new conversation
//        Conversation conversation =
//                conversationService.getOrCreateConversation(
//                        request.getConversationId(),
//                        currentUser
//                );
//
//        // Generate title only once
//        if (conversation.getTitle() == null ||
//                conversation.getTitle().isBlank()) {
//
//            titleService.generateTitle(
//                    conversation,
//                    request.getMessage()
//            );
//        }
//
//        // Save user message
//        messageService.saveUserMessage(
//                conversation,
//                request.getMessage()
//        );
//
//        // Load previous history
//        List<Message> history =
//                messageService.getConversationHistory(
//                        conversation
//                );
//
//        // Search in Vector Store
//        String ragContext =
//                ragService.ask(
//                        request.getMessage(),
//                        currentUser.getId()
//                ).getAnswer();
//
//        // ===============================
//        // Build Prompt
//        // ===============================
//
//        StringBuilder prompt = new StringBuilder();
//
//        prompt.append("""
//                You are an Enterprise AI Assistant.
//
//                Follow these rules:
//
//                1. Answer naturally.
//                2. Use previous conversation if required.
//                3. Use RAG context whenever it is relevant.
//                4. If RAG context is empty, answer from your own knowledge.
//                5. Never mention whether the answer came from RAG or the LLM.
//                6. Keep responses clear and professional.
//
//                """);
//
//        // -------------------------------
//        // Conversation History
//        // -------------------------------
//
//        if (!history.isEmpty()) {
//
//            prompt.append("\nConversation History:\n\n");
//
//            for (Message message : history) {
//
//                prompt.append(message.getRole().name())
//                        .append(": ")
//                        .append(message.getContent())
//                        .append("\n");
//            }
//        }
//
//        // -------------------------------
//        // RAG Context
//        // -------------------------------
//
//        if (ragContext != null
//                && !ragContext.isBlank()
//                && !"No relevant information found.".equalsIgnoreCase(ragContext)) {
//
//            prompt.append("\nRelevant Documents:\n\n");
//            prompt.append(ragContext);
//            prompt.append("\n\n");
//        }
//
//        // -------------------------------
//        // Current User Question
//        // -------------------------------
//
//        prompt.append("Current User Message:\n");
//        prompt.append(request.getMessage());
//
//        // ===============================
//        // Call LLM
//        // ===============================
//
//        String aiResponse = chatClient
//                .prompt()
//                .user(prompt.toString())
//                .tools(
//                        calculatorTool,
//                        weatherTool
//                )
//                .call()
//                .content();
//
//        // ===============================
//        // Save Assistant Message
//        // ===============================
//
//        messageService.saveAssistantMessage(
//                conversation,
//                aiResponse
//        );
//
//        // ===============================
//        // Build Response
//        // ===============================
//
//        ChatResponse response = new ChatResponse();
//
//        response.setConversationId(
//                conversation.getConversationId());
//
//        response.setTitle(
//                conversation.getTitle());
//
//        response.setResponse(
//                aiResponse);
//
//        return response;
//
//    }
//    // ============================================================
//    // Helper Method : Build Conversation History
//    // ============================================================
//
//    private String buildConversationHistory(List<Message> history) {
//
//        if (history == null || history.isEmpty()) {
//            return "";
//        }
//
//        StringBuilder builder = new StringBuilder();
//
//        for (Message message : history) {
//
//            builder.append(message.getRole().name())
//                    .append(": ")
//                    .append(message.getContent())
//                    .append("\n");
//
//        }
//
//        return builder.toString();
//
//    }
//
//    // ============================================================
//    // Helper Method : Safe RAG Search
//    // ============================================================
//
//    private String getRagContext(String question, Long userId) {
//
//        try {
//
//            return ragService
//                    .ask(question, userId)
//                    .getAnswer();
//
//        } catch (Exception e) {
//
//            return "";
//
//        }
//
//    }
//
//    // ============================================================
//    // Streaming API (Future Ready)
//    // ============================================================
//
//    public Flux<String> stream(ChatRequest request) {
//
//        User currentUser = userService.getCurrentUser();
//
//        Conversation conversation =
//                conversationService.getOrCreateConversation(
//                        request.getConversationId(),
//                        currentUser
//                );
//
//        messageService.saveUserMessage(
//                conversation,
//                request.getMessage()
//        );
//
//        StringBuilder responseBuilder = new StringBuilder();
//
//        Flux<String> stream = chatClient
//                .prompt()
//                .user(request.getMessage())
//                .tools(
//                        calculatorTool,
//                        weatherTool
//                )
//                .stream()
//                .content();
//
//        return stream
//
//                .doOnNext(responseBuilder::append)
//
//                .doOnComplete(() ->
//
//                        messageService.saveAssistantMessage(
//
//                                conversation,
//
//                                responseBuilder.toString()
//
//                        )
//
//                );
//
//    }
//
//    // ============================================================
//    // Health Check
//    // ============================================================
//
//    public String ping() {
//
//        return "Enterprise AI Agent Running";
//
//    }
//
//}




package com.aiagent.enterprise_ai_agent.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiagent.enterprise_ai_agent.config.DTO.ApiResponse;
import com.aiagent.enterprise_ai_agent.config.DTO.ChatRequest;
import com.aiagent.enterprise_ai_agent.config.DTO.ChatResponse;
import com.aiagent.enterprise_ai_agent.config.DTO.StreamResponse;
import com.aiagent.enterprise_ai_agent.config.PromptConfig;
import com.aiagent.enterprise_ai_agent.entity.Conversation;
import com.aiagent.enterprise_ai_agent.entity.Message;
import com.aiagent.enterprise_ai_agent.entity.Role;
import com.aiagent.enterprise_ai_agent.entity.User;
import com.aiagent.enterprise_ai_agent.tools.CalculatorTool;
import com.aiagent.enterprise_ai_agent.tools.WeatherTool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AgentService {

    private final ChatClient chatClient;

    private final UserService userService;

    private final ConversationService conversationService;

    private final MessageService messageService;

    private final TitleService titleService;

    private final RagService ragService;

    private final CalculatorTool calculatorTool;

    private final WeatherTool weatherTool;
    
    private final PromptService promptService;
    private final MemoryService memoryService;

    /**
     * ChatGPT Style Single API
     */
    public ChatResponse chat(ChatRequest request) {

        // Logged-in User
        User currentUser = userService.getCurrentUser();

        // Existing conversation OR new conversation
        ApiResponse<Conversation> response =
                conversationService.getOrCreateConversation(
                        request.getConversationId(),
                        currentUser);
        Conversation conversation =
                response.getData();

        // Conversation Security
        if (!conversation.getUser().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You are not allowed to access this conversation.");
        }

        // Load Previous History
        List<Message> history =
                messageService.getRecentMessages(conversation);

        // Search User Documents (RAG)
        String ragContext =
                ragService.buildContext(
                        request.getMessage(),
                        currentUser.getId());

        // Build Final Prompt
        String prompt =
        		 promptService.buildPrompt(
        	                history,
        	                ragContext,
        	                request.getMessage());

        log.info(
                "Conversation : {} | User : {}",
                conversation.getConversationId(),
                currentUser.getEmail());

        // ========= AI Call =========

        String aiResponse =
                chatClient
                        .prompt()
                        .system(PromptConfig.SYSTEM_PROMPT)
                        .user(prompt)
                        .tools(
                                calculatorTool,
                                weatherTool)
                        .call()
                        .content();
        // ========= Save User Message =========

        messageService.save(
                conversation,
                Role.USER,
                request.getMessage());

        // ========= Save Assistant Message =========

        messageService.save(
                conversation,
                Role.ASSISTANT,
                aiResponse);
        conversationService.save(conversation);

        if (memoryService.shouldUpdateSummary(conversation)) {

            memoryService.updateSummary(conversation);

        }
        List<String> sources =
                ragService.getSources(
                        request.getMessage(),
                        currentUser.getId());

        // ========= Generate Title =========

        if (conversation.getTitle() == null
                || conversation.getTitle().isBlank()) {

            String title =
                    titleService.generateTitle(
                            request.getMessage());

            conversation.setTitle(title);

            conversationService.save(conversation);
        }

        // ========= Response =========

        ChatResponse response1 =
                new ChatResponse();

        response1.setConversationId(
                conversation.getConversationId());

        response1.setResponse(
                aiResponse);
        response1.setSources(sources);

        return response1;

    }

    /**
     * Build Final Prompt
     */
    private String buildPrompt(

            List<Message> history,

            String ragContext,

            String userMessage) {

        StringBuilder prompt =
                new StringBuilder();

        // ===== System Prompt =====

        prompt.append(PromptConfig.SYSTEM_PROMPT)
              .append("\n\n");

        // ===== RAG Context =====

        if (ragContext != null &&
                !ragContext.isBlank()) {

            prompt.append("Knowledge Base:\n")
                  .append(ragContext)
                  .append("\n\n");
        }

        // ===== Conversation History =====

        if (history != null &&
                !history.isEmpty()) {

            prompt.append("Conversation History:\n");

            for (Message message : history) {

                prompt.append(message.getRole().name())
                      .append(": ")
                      .append(message.getContent())
                      .append("\n");

            }

            prompt.append("\n");
        }

        // ===== Current User Message =====

        prompt.append("Current User Message:\n")
              .append(userMessage);

        return prompt.toString();

    }
    /**
     * Validate conversation ownership
     */
    private void validateConversationOwner(
            Conversation conversation,
            User currentUser) {

        if (conversation == null) {
            throw new RuntimeException("Conversation not found.");
        }

        if (!conversation.getUser().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You are not authorized to access this conversation.");
        }
    }
    
    @Transactional
    public Flux<StreamResponse> streamChat(ChatRequest request) {

        User currentUser =
                userService.getCurrentUser();

        ApiResponse<Conversation> response =
                conversationService.getOrCreateConversation(
                        request.getConversationId(),
                        currentUser);

        Conversation conversation =
                response.getData();
        validateConversationOwner(
                conversation,
                currentUser);

        List<Message> history =
                messageService.getRecentMessages(conversation);

        String ragContext =
                ragService.buildContext(
                        request.getMessage(),
                        currentUser.getId());

        String prompt =
                promptService.buildPrompt(
                        history,
                        ragContext,
                        request.getMessage());

        // Save user message first
        messageService.save(
                conversation,
                Role.USER,
                request.getMessage());

        StringBuilder fullResponse =
                new StringBuilder();

        Flux<String> stream =

                chatClient

                        .prompt()

                        .system(PromptConfig.SYSTEM_PROMPT)

                        .user(prompt)

                        .tools(
                                calculatorTool,
                                weatherTool)

                        .stream()

                        .content();

        return stream

        		.doOnNext(fullResponse::append)

        		.map(token ->

        		        new StreamResponse(

        		                "TOKEN",

        		                token

        		        )

        		)

        		.concatWith(

        		        Flux.just(

        		                new StreamResponse(

        		                        "DONE",

        		                        ""

        		                )

        		        )

        		)

        		.doOnComplete(() -> {

        		    messageService.save(

        		            conversation,

        		            Role.ASSISTANT,

        		            fullResponse.toString()

        		    );

        		});

    }

}
