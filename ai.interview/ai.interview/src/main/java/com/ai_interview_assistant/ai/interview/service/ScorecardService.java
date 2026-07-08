package com.ai_interview_assistant.ai.interview.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.ai_interview_assistant.ai.interview.config.PromptConfig;
import com.ai_interview_assistant.ai.interview.dto.CandidateScorecardResponse;
import com.ai_interview_assistant.ai.interview.dto.InterviewSession;
import com.ai_interview_assistant.ai.interview.repository.InterviewSessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScorecardService {

    private final ChatModel chatModel;

    private final ObjectMapper objectMapper;

    private final InterviewSessionRepository interviewSessionRepository;

    /**
     * Generate Final AI Scorecard
     */
    public CandidateScorecardResponse generateScorecard(
            String candidateName,
            String interviewReport) throws Exception {

        Prompt prompt = new Prompt(
        		PromptConfig.CandidateScorecardResponse_PROMPT.formatted(interviewReport));

        String json = chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();

        // Remove markdown if AI returns ```json
        json = json.replace("```json", "")
                .replace("```", "")
                .trim();

        int start = json.indexOf("{");
        int end = json.lastIndexOf("}");
        System.out.println(json);
        if (start == -1 || end == -1) {
            throw new RuntimeException("Invalid AI Response");
        }

        json = json.substring(start, end + 1);

        CandidateScorecardResponse response =
                objectMapper.readValue(json, CandidateScorecardResponse.class);

        // Save Interview
        InterviewSession session = new InterviewSession();

        session.setCandidateName(candidateName);
        session.setOverallScore((double) response.getOverallScore());
        session.setPerformance(response.getPerformance());
        session.setHiringRecommendation(response.getHiringRecommendation());
        session.setInterviewDate(LocalDateTime.now());

        interviewSessionRepository.save(session);

        return response;
    }

    
    public List<InterviewSession> getHistory() {

        return interviewSessionRepository.findAll();

    }

    
    public InterviewSession getById(Long id) {

        return interviewSessionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Interview Not Found"));

    }

    
    public void delete(Long id) {

        InterviewSession session = interviewSessionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Interview Not Found"));

        interviewSessionRepository.delete(session);

    }

}