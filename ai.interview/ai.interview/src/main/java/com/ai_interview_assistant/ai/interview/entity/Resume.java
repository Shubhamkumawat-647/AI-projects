package com.ai_interview_assistant.ai.interview.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Resume {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String experience;

}