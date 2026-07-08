package com.ai_interview_assistant.ai.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai_interview_assistant.ai.interview.dto.InterviewSession;

public interface InterviewSessionRepository
        extends JpaRepository<InterviewSession,Long>{

}