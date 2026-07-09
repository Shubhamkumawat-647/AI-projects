package com.aiagent.enterprise_ai_agent.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class LocalStorageService implements StorageService {

    @Value("${app.storage.location:uploads}")
    private String uploadDirectory;

    private Path root;

    @PostConstruct
    public void init() throws IOException {

        root = Paths.get(uploadDirectory).toAbsolutePath().normalize();

        Files.createDirectories(root);

    }

    @Override
    public String save(MultipartFile file) throws IOException {

        String extension =
                StringUtils.getFilenameExtension(file.getOriginalFilename());

        String storedFileName =
                UUID.randomUUID() +
                        (extension == null ? "" : "." + extension);

        Path target = root.resolve(storedFileName);

        try (InputStream inputStream = file.getInputStream()) {

            Files.copy(
                    inputStream,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

        }

        return storedFileName;

    }

    @Override
    public Resource load(String fileName) {

        try {

            Path file = root.resolve(fileName);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {

                return resource;

            }

            throw new RuntimeException("File not found : " + fileName);

        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }

    }

    @Override
    public void delete(String fileName) throws IOException {

        Files.deleteIfExists(root.resolve(fileName));

    }

    @Override
    public Path getPath(String fileName) {

        return root.resolve(fileName);

    }

}