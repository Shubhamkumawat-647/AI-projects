package com.ai_interview_assistant.ai.interview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai_interview_assistant.ai.interview.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume,Long>{

}