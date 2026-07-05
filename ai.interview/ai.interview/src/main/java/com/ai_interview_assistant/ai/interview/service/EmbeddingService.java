package com.ai_interview_assistant.ai.interview.service;

import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public float[] generateEmbedding(String text) {

    	
    	float[] embedding = embeddingModel.embed(text);

        System.out.println("Vector Size : " + embedding.length);

        return embedding;
        
//        return embeddingModel.embed(text);

    }

}