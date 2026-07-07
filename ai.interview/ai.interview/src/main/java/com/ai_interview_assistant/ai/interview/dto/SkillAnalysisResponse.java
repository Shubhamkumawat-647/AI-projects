package com.ai_interview_assistant.ai.interview.dto;

import lombok.Data;

import java.util.List;

@Data
public class SkillAnalysisResponse {

    private List<String> primarySkills;

    private List<String> secondarySkills;

    private List<String> missingSkills;

}