package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.repos.NoteRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Note findById(int id) {
        return noteRepo.findById(id);
    }

    @Override
    public void insert(Note note) {
        noteRepo.insert(note);
    }

    @Override
    public void delete(int id) {
        noteRepo.delete(id);
    }

    @Override
    public void update(Note note) {
        Optional.ofNullable(noteRepo.findById(note.getNoteid())).ifPresent(n -> {
            n.setNotetitle(note.getNotetitle());
            n.setNotedescription(note.getNotedescription());
            noteRepo.update(n);
        });
    }
}
