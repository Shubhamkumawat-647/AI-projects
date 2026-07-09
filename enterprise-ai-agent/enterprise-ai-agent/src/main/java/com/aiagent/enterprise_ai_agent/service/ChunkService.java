package com.aiagent.enterprise_ai_agent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ChunkService {

    public List<String> split(
            String text){

        int chunkSize = 1000;

        List<String> chunks =
                new ArrayList<>();

        for(int i=0;
            i<text.length();
            i+=chunkSize){

            chunks.add(

                    text.substring(

                            i,

                            Math.min(

                                    i+chunkSize,

                                    text.length()

                            )

                    )

            );

        }

        return chunks;

    }

}