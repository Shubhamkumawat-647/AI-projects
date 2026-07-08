package com.ai_interview_assistant.ai.interview.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ai_interview_assistant.ai.interview.dto.CandidateScorecardResponse;
import com.ai_interview_assistant.ai.interview.dto.InterviewSession;
import com.ai_interview_assistant.ai.interview.dto.ScorecardRequest;
import com.ai_interview_assistant.ai.interview.service.ScorecardService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/scorecard")
@AllArgsConstructor
public class ScorecardController {

    private final ScorecardService scorecardService;

    @PostMapping
    public CandidateScorecardResponse generate(@RequestBody ScorecardRequest request)
            throws Exception {

        return scorecardService.generateScorecard(
                request.getCandidateName(),
                request.getInterviewReport());

    }
    @GetMapping("/history")
    public List<InterviewSession> history(){

        return scorecardService.getHistory();

    }

}