package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;

import java.util.List;

public interface CredentialService {
    List<Credential> findByUserId(int userId);

    Credential findById(int id);

    void insert(Credential note);

    void delete(int id);

    void update(Credential note);
}
