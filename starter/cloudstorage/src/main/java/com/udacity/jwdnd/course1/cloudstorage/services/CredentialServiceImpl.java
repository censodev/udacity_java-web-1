package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.repos.CredentialRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CredentialServiceImpl implements CredentialService {
    private final CredentialRepo credentialRepo;
    private final EncryptionService encryptionService;

    public CredentialServiceImpl(CredentialRepo credentialRepo, EncryptionService encryptionService) {
        this.credentialRepo = credentialRepo;
        this.encryptionService = encryptionService;
    }

    @Override
    public List<Credential> findByUserId(int userId) {
        return credentialRepo.findByUserId(userId);
    }

    @Override
    public Credential findById(int id) {
        return credentialRepo.findById(id);
    }

    @Override
    public void insert(Credential credential) {
        credential.setPassword(encryptionService.encrypt(credential.getPassword()));
        credentialRepo.insert(credential);
    }

    @Override
    public void delete(int id) {
        credentialRepo.delete(id);
    }

    @Override
    public void update(Credential credential) {
        Optional.ofNullable(credentialRepo.findById(credential.getCredentialid())).ifPresent(n -> {
            n.setUrl(credential.getUrl());
            n.setUsername(credential.getUsername());
            n.setPassword(encryptionService.encrypt(credential.getPassword()));
            credentialRepo.update(n);
        });
    }
}
