package com.ai_interview_assistant.ai.interview.service;



import java.util.List;



import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;



import com.ai_interview_assistant.ai.interview.util.PdfUtil;



import lombok.RequiredArgsConstructor;



@Service

@RequiredArgsConstructor
public class PdfService58 { 

    private final ChunkService chunkService;

    private final VectorStoreService58 vectorStoreService;

//    public void uploadPdf(MultipartFile file) throws Exception {
//        String text = PdfUtil.extractText(file);
//        List<String> chunks =chunkService.splitText(text);
//        vectorStoreService.saveChunks(chunks);
//    }
    
    public void uploadPdf(MultipartFile file)throws Exception {

        String text = PdfUtil.extractText(file);
        List<String> chunks =chunkService.splitText(text);
        vectorStoreService.saveChunks(chunks,file.getOriginalFilename(),"Resume");
    }
}