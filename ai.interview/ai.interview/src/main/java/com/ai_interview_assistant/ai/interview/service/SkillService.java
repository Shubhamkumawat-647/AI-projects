package com.ai_interview_assistant.ai.interview.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.ai_interview_assistant.ai.interview.config.PromptConfig;
import com.ai_interview_assistant.ai.interview.dto.SkillAnalysisResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SkillService {

    private final ChatModel chatModel;

    private final ObjectMapper objectMapper;

    public SkillAnalysisResponse analyzeSkills(String resumeText) throws Exception {

        Prompt prompt = new Prompt(
        		PromptConfig.Skill_PROMPT.formatted(resumeText));

        String json = chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();

        int start = json.indexOf("{");
        int end = json.lastIndexOf("}");
        System.out.println(json);

        json = json.substring(start, end + 1);

        return objectMapper.readValue(
                json,
                SkillAnalysisResponse.class);
    }
}