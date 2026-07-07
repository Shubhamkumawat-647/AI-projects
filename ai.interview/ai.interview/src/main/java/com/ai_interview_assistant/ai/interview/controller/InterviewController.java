package com.ai_interview_assistant.ai.interview.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ai_interview_assistant.ai.interview.dto.InterviewQuestionResponse;
import com.ai_interview_assistant.ai.interview.service.InterviewService;
import com.ai_interview_assistant.ai.interview.util.PdfUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/interview")
@AllArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/generate")

    public InterviewQuestionResponse generate(@RequestParam MultipartFile file)throws Exception{
    	String resumeText = PdfUtil.extractText(file);

        return interviewService.generateQuestions(resumeText);

    }

}