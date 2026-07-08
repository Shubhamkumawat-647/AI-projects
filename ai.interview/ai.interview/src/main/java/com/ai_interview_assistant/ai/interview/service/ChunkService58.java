package com.ai_interview_assistant.ai.interview.service;



import java.util.ArrayList;

import java.util.List;



import org.springframework.stereotype.Service;



@Service

public class ChunkService58 { 



    private static final int CHUNK_SIZE = 500;



    public List<String> splitText(String text) {



        List<String> chunks = new ArrayList<>();



        int start = 0;



        while (start < text.length()) {



            int end = Math.min(start + CHUNK_SIZE, text.length());



            chunks.add(text.substring(start, end));



            start = end;



        }



        return chunks;



    }



}