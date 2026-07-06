package com.ai_interview_assistant.ai.interview.service;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PdfService {

    private final ChunkService chunkService;

    public PdfService(ChunkService chunkService) {

        this.chunkService = chunkService;

    }

    public List<String> extractChunks(MultipartFile file)
            throws IOException {

        try (PDDocument document =
                Loader.loadPDF(file.getBytes())) {

            PDFTextStripper stripper =
                    new PDFTextStripper();

            String text = stripper.getText(document);

            return chunkService.splitText(text);

        }

    }

}