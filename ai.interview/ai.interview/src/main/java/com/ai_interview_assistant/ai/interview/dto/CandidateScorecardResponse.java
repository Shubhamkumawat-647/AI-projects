package com.ai_interview_assistant.ai.interview.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@Data
public class CandidateScorecardResponse {

    @JsonProperty("Overall Score")
    private int overallScore;

    @JsonProperty("Performance")
    private String performance;

    @JsonProperty("Strengths")
    private List<String> strengths;

    @JsonProperty("Weaknesses")
    private List<String> weaknesses;

    @JsonProperty("Recommendations")
    private List<String> recommendations;

    @JsonProperty("Hiring Recommendation")
    private String hiringRecommendation;
}