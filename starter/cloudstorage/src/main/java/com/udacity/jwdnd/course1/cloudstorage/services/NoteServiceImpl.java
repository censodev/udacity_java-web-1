package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.repos.NoteRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepo noteRepo;

    public NoteServiceImpl(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @Override
    public List<Note> findByUserId(int userId) {
        return noteRepo.findByUserId(userId);
    }

    @Override
    public void save(Note note) {
        noteRepo.insert(note);
    }

    @Override
    public void delete(int id) {
        noteRepo.delete(id);
    }
}
