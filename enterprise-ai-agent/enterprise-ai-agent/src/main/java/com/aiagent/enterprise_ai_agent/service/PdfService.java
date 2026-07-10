package com.aiagent.enterprise_ai_agent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aiagent.enterprise_ai_agent.util.PdfUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final ChunkService chunkService;

    private final VectorStoreService vectorStoreService;

    public void upload(
            MultipartFile file,
            Long userId,
            String conversationId,String fileId) throws Exception {

        String text = PdfUtil.extractText(file);

        List<String> chunks = chunkService.split(text);

        vectorStoreService.save(
                chunks,
                userId,
                conversationId,
                file.getOriginalFilename(),
                "PDF",fileId
        );
    }

}