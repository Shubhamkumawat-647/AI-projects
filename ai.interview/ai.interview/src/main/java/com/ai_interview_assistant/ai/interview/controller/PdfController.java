package com.ai_interview_assistant.ai.interview.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ai_interview_assistant.ai.interview.service.PdfService;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/chunks")
    public List<String> upload(
            @RequestParam MultipartFile file)
            throws IOException {

        return pdfService.extractChunks(file);

    }

}