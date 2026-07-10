package com.aiagent.enterprise_ai_agent.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    /**
     * Save uploaded file.
     *
     * @return stored file name
     */
    String save(MultipartFile file) throws IOException;

    /**
     * Load stored file.
     */
    Resource load(String fileName);

    /**
     * Delete stored file.
     */
    void delete(String fileName) throws IOException;

    /**
     * Get absolute file path.
     */
    Path getPath(String fileName);

	Resource loadAsResource(String fileName) throws MalformedURLException;
    
    
    

}