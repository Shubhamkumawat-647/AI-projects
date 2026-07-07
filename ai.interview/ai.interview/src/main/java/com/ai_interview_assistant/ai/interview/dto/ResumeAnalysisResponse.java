package com.ai_interview_assistant.ai.interview.dto;

import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ResumeAnalysisResponse {

    private String name;

    private String email;

    @JsonProperty("mobile")
    private String phone;

    private List<String> skills;

    private List<Education> education;

    private List<Experience> experience;

    private List<Project> projects;

    private List<Certification> certifications;


}