package com.aiagent.enterprise_ai_agent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.aiagent.enterprise_ai_agent.config.PromptConfig;
import com.aiagent.enterprise_ai_agent.config.DTO.RagResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RagService {

    private final VectorStore vectorStore;

    private final ChatModel chatModel;

    public RagResponse ask(String question,Long userId){

        List<Document> documents =
                vectorStore.similaritySearch(
                        SearchRequest.builder()
                                .query(question)
                                .topK(5)
                                .filterExpression(
                                        "userId == " + userId
                                )
                                .build()
                );

        if(documents == null || documents.isEmpty()){

            return new RagResponse(
                    "No relevant information found.",
                    List.of());

        }

        StringBuilder context =
                new StringBuilder();

        List<String> sources =
                new ArrayList<>();

        for(Document document : documents){

            context.append(document.getText())
                    .append("\n\n");

            sources.add(
                    String.valueOf(
                            document.getMetadata()
                                    .get("fileName")
                    )
            );

        }

        Prompt prompt =
                new Prompt(

                        PromptConfig.RAG_PROMPT.formatted(

                                context,

                                question

                        )

                );

        String answer =
                chatModel.call(prompt)
                        .getResult()
                        .getOutput()
                        .getText();

        return new RagResponse(
                answer,
                sources
        );

    }
    public String buildContext(
            String question,
            Long userId) {

        List<Document> documents =
                vectorStore.similaritySearch(

                        SearchRequest.builder()
                                .query(question)
                                .topK(5)
                                .filterExpression(
                                        "userId == " + userId)
                                .build());

        if (documents == null || documents.isEmpty()) {
            return "";
        }

        StringBuilder context = new StringBuilder();

        for (Document document : documents) {

            context.append(document.getText())
                    .append("\n\n");

        }

        return context.toString();

    }

    /**
     * Returns document names used for answering.
     */
    public List<String> getSources(
            String question,
            Long userId) {

        List<Document> documents =
                vectorStore.similaritySearch(

                        SearchRequest.builder()
                                .query(question)
                                .topK(5)
                                .filterExpression(
                                        "userId == " + userId)
                                .build());

        List<String> sources = new ArrayList<>();

        if (documents == null) {
            return sources;
        }

        for (Document document : documents) {

            Object fileName =
                    document.getMetadata().get("fileName");

            if (fileName != null &&
                    !sources.contains(fileName.toString())) {

                sources.add(fileName.toString());

            }

        }

        return sources;

    }



}