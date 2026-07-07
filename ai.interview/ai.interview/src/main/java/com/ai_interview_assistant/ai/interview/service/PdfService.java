package com.ai_interview_assistant.ai.interview.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai_interview_assistant.ai.interview.dto.FileUploadResponse;
import com.ai_interview_assistant.ai.interview.entity.DocumentEntity;
import com.ai_interview_assistant.ai.interview.repository.DocumentRepository;

@Service
public class PdfService {

    private final ChunkService chunkService;
    private final VectorStore vectorStore;
    private final DocumentRepository documentRepository;

    public PdfService(ChunkService chunkService,
                      VectorStore vectorStore,DocumentRepository documentRepository) {

        this.chunkService = chunkService;
        this.vectorStore = vectorStore;
        this.documentRepository= documentRepository;
    }

    /**
     * Upload PDF and Store Embeddings into PostgreSQL
     */
    public FileUploadResponse uploadPdf(MultipartFile file) throws IOException {
    	
    	DocumentEntity entity =new DocumentEntity();

    	entity.setDocumentId(UUID.randomUUID().toString());
    	entity.setFileName(file.getOriginalFilename());
    	entity.setDocumentType("Resume");
    	entity.setUploadedBy("Shubham");
    	entity.setUploadedAt(LocalDateTime.now());

    	documentRepository.save(entity);

        // Step 1 : Extract Text
        String text = extractText(file);

        // Step 2 : Split into Chunks
        List<String> chunks = chunkService.splitText(text);

        System.out.println("Total Chunks : " + chunks.size());

        // Step 3 : Convert Chunks to Documents
        List<Document> documents = new ArrayList<>();

        for (String chunk : chunks) {

//        	===without matadata
//            documents.add(new Document(chunk));
//            ---with metadata
            Map<String,Object> metadata =new HashMap<>();

            metadata.put("fileName",entity.getFileName());
            metadata.put("documentId",entity.getDocumentId());
            metadata.put("uploadedAt",LocalDate.now().toString());

            Document document =new Document(chunk, metadata);
            System.out.println(document.getMetadata());

            documents.add(document);

        }

        // Step 4 : Store into Vector Database
        vectorStore.add(documents);

        System.out.println("Embeddings Stored Successfully.");
		return FileUploadResponse.builder().fileName(entity.getFileName()).documentId(entity.getDocumentId()).message("uploaded").build();
    }

    /**
     * Extract Text From PDF
     */
    public String extractText(MultipartFile file) throws IOException {

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {

            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(document);

            return text;

        }

    }

}