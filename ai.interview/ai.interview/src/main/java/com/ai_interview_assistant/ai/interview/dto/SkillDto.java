package com.ai_interview_assistant.ai.interview.dto;

import lombok.Data;

@Data
public class SkillDto {

    private String skill;

    private String level;

    private Integer confidence;

}