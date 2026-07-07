package com.ai_interview_assistant.ai.interview.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai_interview_assistant.ai.interview.dto.ResumeAnalysisResponse;
import com.ai_interview_assistant.ai.interview.service.ResumeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/resume")
@AllArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    
    @PostMapping(value="/analyze",consumes="multipart/form-data")
    public ResumeAnalysisResponse analyze(@RequestParam MultipartFile file) throws Exception{

    	        return resumeService.analyzeResume(file);

    	    }

}