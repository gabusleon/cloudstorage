package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public int createNote(Notes note){
        return this.notesMapper.insertNote(note);
    }

    public int updateNote(Notes note){
        return this.notesMapper.updateNoteById(note);
    }

    public List<Notes> readNotes(Integer userid){
        return this.notesMapper.getNotes(userid);
    }

    public Notes readNote(Integer noteid){
        return this.notesMapper.getNote(noteid);
    }

    public int deleteNote(Integer noteid){
        return  this.notesMapper.deleteNoteById(noteid);
    }


}
