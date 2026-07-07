package com.ai_interview_assistant.ai.interview.dto;

import java.util.List;

import lombok.Data;

@Data
public class Project {

    private String name;

    private List<String> technologies;

    private List<String> description;

}