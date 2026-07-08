package com.ai_interview_assistant.ai.interview.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VectorStoreService {

    private final VectorStore vectorStore;

    public void saveText(String text) {

        Document document = new Document(text);

        vectorStore.add(List.of(document));

    }
    public void saveDocuments() {

        List<Document> docs = List.of(

                new Document("Java is Object Oriented."),

                new Document("Spring Boot supports REST APIs."),

                new Document("Microservices use REST and Kafka."),

                new Document("Docker packages applications.")

        );

        vectorStore.add(docs);

    }

}