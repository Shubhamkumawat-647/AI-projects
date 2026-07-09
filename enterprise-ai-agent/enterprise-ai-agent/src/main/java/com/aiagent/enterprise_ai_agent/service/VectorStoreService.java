package com.aiagent.enterprise_ai_agent.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VectorStoreService {

    private final VectorStore vectorStore;

    public void save(

            List<String> chunks,

            Long userId,

            String conversationId,

            String fileName,

            String documentType){

        List<Document> documents =new ArrayList<>();

        int chunkNo = 1;

        for(String chunk : chunks){

            Map<String,Object> metadata =
                    new HashMap<>();
            metadata.put("userId", userId);
            metadata.put("conversationId", conversationId);

            metadata.put(
                    "fileName",
                    fileName);

            metadata.put(
                    "documentType",
                    documentType);

            metadata.put(
                    "chunkNo",
                    chunkNo++);
            
            metadata.put(
            	    "uploadedAt",
            	    LocalDateTime.now().toString()
            	);
            Document document =
                    new Document(
                            chunk,
                            metadata);
            

            documents.add(document);

        }

        vectorStore.add(documents);

    }

}