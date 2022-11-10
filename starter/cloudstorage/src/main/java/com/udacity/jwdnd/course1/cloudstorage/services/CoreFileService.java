package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.repos.FileRepo;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;

public abstract class CoreFileService implements FileService {
    protected final FileRepo fileRepo;

    protected CoreFileService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    @Override
    public List<File> findByUserId(int userId) {
        return fileRepo.findByUserId(userId);
    }

    @Override
    public File findById(int id) {
        return fileRepo.findById(id);
    }

    protected void saveMetadata(File metadata) {
        fileRepo.insert(metadata);
    }

    protected void deleteMetadata(int id) {
        fileRepo.delete(id);
    }

    protected String generateFileName(String originName) {
        var now =  String.valueOf(Instant.now().toEpochMilli());
        try {
            var random = String.format("%06d", SecureRandom.getInstanceStrong().nextInt(999999));
            return now.concat(random).concat(originName);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
