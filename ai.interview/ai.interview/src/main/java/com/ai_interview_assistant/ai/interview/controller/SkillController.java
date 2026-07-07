package com.ai_interview_assistant.ai.interview.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai_interview_assistant.ai.interview.dto.SkillAnalysisResponse;
import com.ai_interview_assistant.ai.interview.service.SkillService;
import com.ai_interview_assistant.ai.interview.util.PdfUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/skills")
@AllArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping("/analyze")

    public SkillAnalysisResponse analyze(@RequestParam MultipartFile file)throws Exception{
    	String resumeText = PdfUtil.extractText(file);
        return skillService.analyzeSkills(
                resumeText);

    }

}