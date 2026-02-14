//package com.apnapg.service.impl;
//
//import com.apnapg.exceptions.BadRequestException;
//import com.apnapg.service.FileStorageService;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.Set;
//import java.util.UUID;
//
//@Service
//@Slf4j
//public class FileStorageServiceImpl implements FileStorageService {
//
//    @Value("${app.upload.dir}")
//    private String uploadDir;
//
//    private Path storagePath;
//
//    private static final Set<String> ALLOWED_TYPES = Set.of(
//            "image/jpeg",
//            "image/png",
//            "image/webp",
//            "application/pdf"
//    );
//
//    // ======================================================
//    // INIT DIRECTORY
//    // ======================================================
//    @PostConstruct
//    public void init() {
//        try {
//            storagePath = Paths.get(uploadDir).toAbsolutePath().normalize();
//            Files.createDirectories(storagePath);
//            log.info("File storage initialized at {}", storagePath);
//        } catch (IOException e) {
//            throw new RuntimeException("Could not initialize file storage", e);
//        }
//    }
//
//    // ======================================================
//    // STORE FILE
//    // ======================================================
//    @Override
//    public String store(MultipartFile file) {
//
//        if (file == null || file.isEmpty())
//            throw new BadRequestException("File cannot be empty");
//
//        if (!ALLOWED_TYPES.contains(file.getContentType()))
//            throw new BadRequestException("Unsupported file type");
//
//        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
//
//        // Prevent path traversal
//        if (originalName.contains(".."))
//            throw new BadRequestException("Invalid file name");
//
//        String extension = originalName.contains(".")
//                ? originalName.substring(originalName.lastIndexOf('.'))
//                : "";
//
//        String newFileName = UUID.randomUUID() + extension;
//
//        try {
//            Path target = storagePath.resolve(newFileName);
//            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
//
//            log.info("File stored: {}", newFileName);
//
//            return newFileName; // store only file name in DB
//
//        } catch (IOException e) {
//            log.error("File storage failed", e);
//            throw new RuntimeException("Failed to store file");
//        }
//    }
//
//    // ======================================================
//    // DELETE FILE
//    // ======================================================
//    @Override
//    public void delete(String fileUrl) {
//
//        if (fileUrl == null || fileUrl.isBlank()) return;
//
//        try {
//            Path filePath = storagePath.resolve(fileUrl).normalize();
//            Files.deleteIfExists(filePath);
//            log.info("File deleted: {}", fileUrl);
//        } catch (IOException e) {
//            log.warn("File deletion failed: {}", fileUrl);
//        }
//    }
//}
package com.apnapg.service.impl;

import com.apnapg.exceptions.BadRequestException;
import com.apnapg.service.FileStorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.max-size}")
    private long maxFileSize; // in bytes

    private Path storagePath;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp",
            "application/pdf"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            ".jpg", ".jpeg", ".png", ".webp", ".pdf"
    );

    // ======================================================
    // INIT DIRECTORY
    // ======================================================
    @PostConstruct
    public void init() {
        try {
            storagePath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(storagePath);
            log.info("File storage initialized at {}", storagePath);
        } catch (IOException e) {
            throw new IllegalStateException("Could not initialize file storage", e);
        }
    }

    // ======================================================
    // STORE FILE
    // ======================================================
//    @Override
//    public String store(MultipartFile file) {
//
//        if (file == null || file.isEmpty())
//            throw new BadRequestException("File cannot be empty");
//
//        if (file.getSize() > maxFileSize)
//            throw new BadRequestException("File size exceeds limit");
//
//        if (!ALLOWED_TYPES.contains(file.getContentType()))
//            throw new BadRequestException("Unsupported file type");
//
//        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
//
//        if (originalName == null || originalName.contains(".."))
//            throw new BadRequestException("Invalid file name");
//
//        String extension = originalName.contains(".")
//                ? originalName.substring(originalName.lastIndexOf(".")).toLowerCase()
//                : "";
//
//        if (!ALLOWED_EXTENSIONS.contains(extension))
//            throw new BadRequestException("Invalid file extension");
//
//        String newFileName = UUID.randomUUID() + extension;
//
//        try (InputStream inputStream = file.getInputStream()) {
//
//            Path target = storagePath.resolve(newFileName).normalize();
//
//            // Ensure file stays inside upload directory
//            if (!target.startsWith(storagePath))
//                throw new BadRequestException("Invalid file path");
//
//            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
//
//            log.info("File stored successfully: {}", newFileName);
//
//            return newFileName;
//
//        } catch (IOException e) {
//            log.error("File storage failed", e);
//            throw new RuntimeException("Failed to store file");
//        }
//    }
    @Override
    public String store(MultipartFile file) {

        if (file == null || file.isEmpty())
            throw new BadRequestException("File cannot be empty");

        if (file.getSize() > maxFileSize)
            throw new BadRequestException("File size exceeds limit");

        String originalName = StringUtils.cleanPath(file.getOriginalFilename());

        if (originalName == null || originalName.contains(".."))
            throw new BadRequestException("Invalid file name");

        String extension = originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf(".")).toLowerCase()
                : "";

        if (!ALLOWED_EXTENSIONS.contains(extension))
            throw new BadRequestException("Invalid file extension");

        String newFileName = UUID.randomUUID() + extension;

        try (InputStream inputStream = file.getInputStream()) {

            Path target = storagePath.resolve(newFileName).normalize();

            if (!target.startsWith(storagePath))
                throw new BadRequestException("Invalid file path");

            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);

            // Extra content validation (safer)
            String detectedType = Files.probeContentType(target);
            if (detectedType == null || !ALLOWED_TYPES.contains(detectedType)) {
                Files.deleteIfExists(target);
                throw new BadRequestException("Invalid file content");
            }

            log.info("File stored successfully: {}", newFileName);

            return newFileName;

        } catch (IOException e) {
            log.error("File storage failed", e);
            throw new BadRequestException("File upload failed");
        }
    }


    // ======================================================
    // DELETE FILE
    // ======================================================
    @Override
    public void delete(String fileName) {

        if (fileName == null || fileName.isBlank())
            return;

        try {
            Path filePath = storagePath.resolve(fileName).normalize();

            if (!filePath.startsWith(storagePath))
                throw new BadRequestException("Invalid file path");

            Files.deleteIfExists(filePath);

            log.info("File deleted: {}", fileName);

        } catch (IOException e) {
            log.warn("File deletion failed: {}", fileName);
        }
    }
}

