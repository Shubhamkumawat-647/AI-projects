package com.ai_interview_assistant.ai.interview.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.ai_interview_assistant.ai.interview.config.PromptConfig;
import com.ai_interview_assistant.ai.interview.dto.AnswerEvaluationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AnswerEvaluationService {

    private final ChatModel chatModel;

    private final ObjectMapper objectMapper;

    public AnswerEvaluationResponse evaluate(
            String question,
            String answer) throws Exception {

        Prompt prompt = new Prompt(
        		PromptConfig.ANSWER_PROMPT.formatted(question, answer));

        String json = chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();

        int start = json.indexOf("{");
        int end = json.lastIndexOf("}");

        if (start == -1 || end == -1) {
            throw new RuntimeException("Invalid AI Response");
        }

        json = json.substring(start, end + 1);

        return objectMapper.readValue(
                json,
                AnswerEvaluationResponse.class);
    }
}