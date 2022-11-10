package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.repos.FileRepo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
public class LocalFileService extends CoreFileService {
    private static final Path ROOT_PATH = Paths.get("uploads");

    public LocalFileService(FileRepo fileRepo) {
        super(fileRepo);
    }

    @PostConstruct
    void postConstruct() {
        if (Files.notExists(ROOT_PATH)) {
            try {
                Files.createDirectory(ROOT_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(InputStream is, File metadata) {
        var filename = generateFileName(Objects.requireNonNull(metadata.getFilename()));
        metadata.setFilename(filename);
        try {
            saveMetadata(metadata);
            Files.copy(is, ROOT_PATH.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource load(int id) {
        try {
            var metadata = findById(id);
            var resource = new UrlResource(ROOT_PATH.resolve(metadata.getFilename()).toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        Optional.ofNullable(findById(id)).ifPresent(f -> {
            try {
                Files.deleteIfExists(ROOT_PATH.resolve(f.getFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            deleteMetadata(id);
        });
    }
}
