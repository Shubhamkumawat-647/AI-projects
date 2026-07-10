package com.aiagent.enterprise_ai_agent.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aiagent.enterprise_ai_agent.service.PdfService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @PostMapping("/upload")
    public String upload(
            @RequestParam MultipartFile file,@RequestParam Long userId,@RequestParam String conversationId,String fileId)
            throws Exception {

        pdfService.upload(file,userId,conversationId,fileId);

        return "PDF Uploaded Successfully";

    }

}