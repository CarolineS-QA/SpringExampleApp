package com.qa.service;

import com.qa.domain.Note;
import com.qa.exceptions.NoteNotFoundException;
import com.qa.repo.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    public final NotesRepository repo;

    @Autowired
    public NoteService(NotesRepository repo) {
        this.repo = repo;
    }
    public List<Note> readNotes(){
        return this.repo.findAll();
    }

    public Note createNote(Note note){
        return this.repo.save(note);
    }

    public Note findNoteById(Long id){
        return this.repo.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    // This way, it will keep the original data if it has not been changed?
    public Note updateNote(Long id, Note note){
        Note update = findNoteById(id);
        update.setTitle(note.getTitle());
        update.setDescription(note.getDescription());
        return this.repo.save(update);
    }

    /* Returns false if successful, because it no longer exists.
     * This allows for a more meaningful test.
     */
    public boolean deleteNote(Long id){
        if(!this.repo.existsById(id)){
            throw new NoteNotFoundException();
        }
        this.repo.deleteById(id);
        return this.repo.existsById(id);
    }
}
