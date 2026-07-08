package com.ai_interview_assistant.ai.interview.service;



import java.util.List;

import java.util.stream.Collectors;



import org.springframework.ai.chat.model.ChatModel;

import org.springframework.ai.chat.prompt.Prompt;

import org.springframework.ai.document.Document;

import org.springframework.ai.vectorstore.SearchRequest;

import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.stereotype.Service;



import com.ai_interview_assistant.ai.interview.config.PromptConfig;

import com.ai_interview_assistant.ai.interview.dto.RagRequest;

import com.ai_interview_assistant.ai.interview.dto.RagResponse;



import lombok.RequiredArgsConstructor;



@Service

@RequiredArgsConstructor

public class RagService59 { 

    private final VectorStore vectorStore;
    private final ChatModel chatModel;
    
    public RagResponse ask(RagRequest request) {
    	
    	long start = System.currentTimeMillis();
    	
        SearchRequest searchRequest = SearchRequest.builder()
                .query(request.getQuestion())
                .topK(5)
                .filterExpression(
                        "fileName == '" + request.getFileName() + "'")
                .build();
        List<Document> documents =
                vectorStore.similaritySearch(searchRequest);
        if (documents == null || documents.isEmpty()) {
            RagResponse response = new RagResponse();
            response.setAnswer("No relevant information found.");
            response.setSources(List.of());
            return response;
        }
        String history = buildConversationHistory();
        StringBuilder context = new StringBuilder();
        for (Document doc : documents) {
            context.append(doc.getText())
                   .append("\n\n");
        }
        Prompt prompt = new Prompt(
                PromptConfig.RagPrompt.formatted(
                        history,
                        context.toString(),
                        request.getQuestion()
                )
        );
        String answer = chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();

        List<String> sources = documents.stream()
                .map(doc ->
                        doc.getMetadata().get("fileName")
                                + " Page "
                                + doc.getMetadata().get("page")
                )
                .collect(Collectors.toList());
        RagResponse response = new RagResponse();
        response.setAnswer(answer);
        response.setSources(sources);
        response.setRetrievedChunks(documents.size());
        response.setResponseTime(System.currentTimeMillis() - start);
        return response;
    }


    private String buildConversationHistory() {
        return "No previous conversation.";

    }
}