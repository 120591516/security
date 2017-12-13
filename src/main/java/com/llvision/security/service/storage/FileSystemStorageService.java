package com.llvision.security.service.storage;

import com.llvision.security.config.ApplicationProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by llvision on 17/5/3.
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(ApplicationProperties properties) {
        this.rootLocation = Paths.get(properties.getStorage().getLocation());
    }

    @Override
    public void store(byte[] data, String path) throws StorageException {
        try {
            if (data == null || data.length <= 0) {
                throw new StorageException("Failed to store empty file");
            }
            Path p = rootLocation.resolve(path);
            if (!Files.exists(p.getParent())) {
                Files.createDirectories(p.getParent());
            }
            Files.copy(new ByteArrayInputStream(data), p);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + path, e);
        }
    }

    @Override
    public Resource loadAsResource(String path) throws InvalidPathException {
        Path file = rootLocation.resolve(path);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageNotFoundException("Could not read file: " + path);
            }
        } catch (IOException e) {
            throw new StorageNotFoundException("Could not read file: " + path, e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }}
