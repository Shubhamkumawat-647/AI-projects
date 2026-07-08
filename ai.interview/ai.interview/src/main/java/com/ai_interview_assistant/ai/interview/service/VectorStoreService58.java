package com.ai_interview_assistant.ai.interview.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class VectorStoreService58 { 

    private final VectorStore vectorStore;
    
//    public void saveChunks(List<String> chunks) {
//        List<Document> documents = chunks.stream()
//                .map(Document::new)
//                .toList();
//        vectorStore.add(documents);
//    }
    
    public void saveChunks( List<String> chunks,String fileName,String documentType) {

        List<Document> documents = new ArrayList<>();

        int page = 1;

        for (String chunk : chunks) {

            Map<String, Object> metadata = new HashMap<>();
            metadata.put("fileName", fileName);
            metadata.put("documentType", documentType);
            metadata.put("page", page++);
            Document document =new Document(chunk, metadata);
            documents.add(document);
        }
        vectorStore.add(documents);
    }
}