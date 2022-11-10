package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;

import java.util.List;

public interface NoteService {
    List<Note> findByUserId(int userId);

    void save(Note note);

    void delete(int id);
}
