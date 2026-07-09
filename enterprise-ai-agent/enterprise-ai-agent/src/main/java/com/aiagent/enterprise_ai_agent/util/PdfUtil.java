package com.aiagent.enterprise_ai_agent.util;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

public class PdfUtil {

    public static String extractText(MultipartFile file) throws IOException {

        try (PDDocument document = PDDocument.load(file.getInputStream())) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);
        }
    }
}