package com.apnapg.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(byte[] fileBytes, String fileName) {
        try {
            Path dirPath = Paths.get(uploadDir);
            if (!Files.exists(dirPath)) Files.createDirectories(dirPath);

            String safeName = Paths.get(fileName).getFileName().toString();
            String uniqueName = System.currentTimeMillis() + "_" + safeName;
            Path filePath = dirPath.resolve(uniqueName);
            Files.write(filePath, fileBytes);

            log.info("File {} saved successfully", uniqueName);
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("Failed to store file {}", fileName, e);
            throw new RuntimeException("Could not store file: " + fileName, e);
        }
    }
}
