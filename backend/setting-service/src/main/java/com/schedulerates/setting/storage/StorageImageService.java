package com.schedulerates.setting.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.schedulerates.setting.exception.StorageException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class StorageImageService {
    private final Path rootLocation;
    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif",
            "image/webp");

    public StorageImageService(FileStorageProperties properties) {
        try {
            this.rootLocation = Paths.get(properties.getLocation())
                    .toAbsolutePath()
                    .normalize();

            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public String store(MultipartFile file) {
        // Validation checks
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new StorageException("File size exceeds limit of " + MAX_FILE_SIZE + " bytes");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new StorageException("Invalid file type. Allowed types: " + ALLOWED_CONTENT_TYPES);
        }

        // Generate filename and store file
        String originalFilename = file.getOriginalFilename();
        String filename = generateUniqueFilename(
                originalFilename != null ? originalFilename : "unnamed-file");
        try {
            Files.copy(file.getInputStream(),
                    this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + filename, e);
        }
    }

    public Resource load(String filename) {
        try {
            // Security check against path traversal
            if (filename.contains("..")) {
                throw new StorageException("Cannot load file with relative path: " + filename);
            }

            Path file = rootLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("File not found or not readable: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    public void delete(String filename) throws StorageException {
        try {
            Path file = rootLocation.resolve(filename).normalize();

            // Additional security check
            if (!file.startsWith(rootLocation)) {
                throw new StorageException("Cannot delete file outside storage directory");
            }

            if (!Files.exists(file)) {
                throw new StorageException("File not found: " + filename);
            }
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Failed to delete file: " + filename, e);
        }
    }

    private String generateUniqueFilename(String originalFilename) {
        String cleanFilename = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
        return UUID.randomUUID() + "_" + cleanFilename;
    }

    // Optional method to get storage location (useful for debugging)
    public String getStorageLocation() {
        return this.rootLocation.toString();
    }
}