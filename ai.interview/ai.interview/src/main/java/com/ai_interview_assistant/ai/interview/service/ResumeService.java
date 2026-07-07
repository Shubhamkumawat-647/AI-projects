package com.ai_interview_assistant.ai.interview.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai_interview_assistant.ai.interview.config.PromptConfig;
import com.ai_interview_assistant.ai.interview.dto.ResumeAnalysisResponse;
import com.ai_interview_assistant.ai.interview.entity.Resume;
import com.ai_interview_assistant.ai.interview.repository.ResumeRepository;
import com.ai_interview_assistant.ai.interview.util.PdfUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ResumeService {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;
    private final ResumeRepository resumeRepository;

    public ResumeAnalysisResponse analyzeResume(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new RuntimeException("Please upload a PDF.");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new RuntimeException("Only PDF allowed.");
        }

        String resumeText = PdfUtil.extractText(file);

        Prompt prompt = new Prompt(PromptConfig.PROMPT.formatted(resumeText));

        String json = chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();

        int start = json.indexOf("{");
        int end = json.lastIndexOf("}");
        json = json.replaceAll(",\\s*\\.\\.\\.", "");

        if (start == -1 || end == -1) {
            throw new RuntimeException("AI did not return valid JSON.");
        }

        json = json.substring(start, end + 1);

        System.out.println(json);

        try {

            ResumeAnalysisResponse response =
                    objectMapper.readValue(json, ResumeAnalysisResponse.class);

            Resume entity = new Resume();
            entity.setName(response.getName());
            entity.setEmail(response.getEmail());

            resumeRepository.save(entity);

            return response;

        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Invalid AI Response: " + json, ex);
        }
    }
}