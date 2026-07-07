package com.ai_interview_assistant.ai.interview.dto;

import java.util.List;

import lombok.Data;

@Data
public class Experience {

    private String company;
    private String location;
    private String title;
    private String duration;

    private List<String> description;

}