package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

public interface FileService {
    void save(InputStream is, File metadata);

    Resource load(int id);

    void delete(int id);

    List<File> findByUserId(int userId);

    File findById(int id);
}
