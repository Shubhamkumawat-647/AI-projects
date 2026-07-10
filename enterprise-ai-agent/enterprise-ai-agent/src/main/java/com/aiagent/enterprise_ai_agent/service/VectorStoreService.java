package com.aiagent.enterprise_ai_agent.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;


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

            String documentType,String fileId){

        List<Document> documents =new ArrayList<>();

        int chunkNo = 1;

        for(String chunk : chunks){

            Map<String,Object> metadata =
                    new HashMap<>();
            metadata.put("userId", userId);
            metadata.put("conversationId", conversationId);
            metadata.put("fileId", fileId);
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
    public List<Document> search(
            String question,
            Long userId,
            String fileId,
            int topK) {

        return vectorStore.similaritySearch(

                SearchRequest.builder()
                        .query(question)
                        .topK(topK)
                        .filterExpression(
                                "userId == " + userId)
                        .filterExpression(
                                "fileId == " + fileId)
                        
                        .build());
    }

    /**
     * Delete conversation vectors.
     */
    public void deleteByConversationId(String conversationId) {

        vectorStore.delete(
                new FilterExpressionBuilder()
                        .eq("conversationId", conversationId)
                        .build()
        );

    }

    /**
     * Delete all vectors of one user.
     */
    public void deleteByUserId(Long userId) {

        vectorStore.delete(
                new FilterExpressionBuilder()
                        .eq("userId", userId)
                        .build()
        );

    }
    
    public void deleteByFileId(String fileId) {

        vectorStore.delete(
                new FilterExpressionBuilder()
                        .eq("fileId", fileId)
                        .build()
        );

    }

}