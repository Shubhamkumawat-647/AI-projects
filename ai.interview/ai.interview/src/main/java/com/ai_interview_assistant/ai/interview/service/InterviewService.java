package com.ai_interview_assistant.ai.interview.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;


import com.ai_interview_assistant.ai.interview.config.PromptConfig;
import com.ai_interview_assistant.ai.interview.dto.InterviewQuestionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InterviewService {

    private final ChatModel chatModel;

    private final ObjectMapper objectMapper;

    public InterviewQuestionResponse generateQuestions(String resumeText)
            throws Exception {

        Prompt prompt =
                new Prompt(
                		PromptConfig.INTERVIEW_PROMPT.formatted(resumeText));

        String json =
                chatModel.call(prompt)
                        .getResult()
                        .getOutput()
                        .getText();

        int start = json.indexOf("{");
        int end = json.lastIndexOf("}");
        System.out.println(json);
        if(start==-1 || end==-1){

            throw new RuntimeException("Invalid AI Response");

        }

        json = json.substring(start,end+1);

        return objectMapper.readValue(
                json,
                InterviewQuestionResponse.class);

    }

}