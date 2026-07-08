package com.ai_interview_assistant.ai.interview.controller;



import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;



import com.ai_interview_assistant.ai.interview.service.PdfService;
import com.ai_interview_assistant.ai.interview.service.PdfService58;

import lombok.RequiredArgsConstructor;



@RestController

@RequestMapping("/api/pdf")

@RequiredArgsConstructor

public class PdfController58 { 
    private final PdfService58 pdfService;
    
    @PostMapping("/upload58")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file)throws Exception {
    	pdfService.uploadPdf(file);
        return ResponseEntity.ok("PDF Indexed Successfully");
    }
}