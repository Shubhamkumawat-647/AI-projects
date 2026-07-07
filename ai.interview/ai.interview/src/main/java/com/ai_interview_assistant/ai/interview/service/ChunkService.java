package com.ai_interview_assistant.ai.interview.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

@Service
public class ChunkService {

    public List<String> splitText(String text) {

        TokenTextSplitter splitter = new TokenTextSplitter();

        List<Document> documents = splitter.split(List.of(new Document(text)));

        List<String> chunks = documents.stream()
                .map(Document::getText)
                .toList();

        System.out.println("Total Chunks: " + chunks.size());

//        for (int i = 0; i < chunks.size(); i++) {
//            System.out.println("----------------------------------");
//            System.out.println("Chunk " + (i + 1));
//            System.out.println(chunks.get(i));
//            System.out.println("----------------------------------");
//        }
//        

        return chunks;
    }
}