package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotesController {
    private final NotesService notesService;
    private final UserService userService;

    public NotesController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }
/*
    @GetMapping("/notes")
    public String notesPageView(Model model, Authentication authentication){
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null) {
            model.addAttribute("allNotesByUser", notesService.readNotes(userLogged.getUserid()));
            return "home#nav-notes";
        }else{
            return "login";
        }
    }
*/
    @PostMapping("/save-note")
    public String saveNote(@ModelAttribute("noteForm") NoteForm noteForm, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        String notesError = null;
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null){
            Notes note = new Notes();
            note.setNotetitle(noteForm.getNotetitle());
            note.setNotedescription(noteForm.getNotedescription());
            note.setUserid(userLogged.getUserid());
            if(noteForm.getNoteid() != null){
                note.setNoteid(noteForm.getNoteid());
                int updatedRows = notesService.updateNote(note);
                if(updatedRows < 0){
                    notesError = "Failed to updated note!";
                }
            }else {
                int rowsAdded = notesService.createNote(note);
                if (rowsAdded < 0) {
                    notesError = "Failed to create note!";
                }
            }
        }else{
            return "login";
        }
        if(notesError == null){
            model.addAttribute("noteSuccess", "You successfully saved your Note!");
        }else{
            model.addAttribute("noteError", notesError);
        }
        model.addAttribute("allNotesByUser", notesService.readNotes(userLogged.getUserid()));
        redirectAttributes.addFlashAttribute("activeTab", "notes");

        return "redirect:/home";
    }

    @GetMapping("/delete-note/{noteid}")
    public String deleteNote(@PathVariable("noteid") String noteid, @ModelAttribute("noteForm") NoteForm noteForm, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        Users userLogged = userService.getUser(authentication.getName());

        if(userLogged != null) {
            int rowsDeleted = notesService.deleteNote(Integer.parseInt(noteid));
            if(rowsDeleted < 0){
                model.addAttribute("noteError", "Failed to delete your Note!");
            }else{
                model.addAttribute("noteSuccess", "You successfully deleted your Note!");
            }
            model.addAttribute("allNotesByUser", notesService.readNotes(userLogged.getUserid()));
            redirectAttributes.addFlashAttribute("activeTab", "notes");

            return "redirect:/home";
        }else{
            return "login";
        }
    }
/*
    @GetMapping("/show-note/{noteid}")
    public String showNote(@ModelAttribute("note") Notes note, @PathVariable("noteid") String noteid, Model model){
        note = notesService.readNote(Integer.parseInt(noteid));
        if(note != null) {
            model.addAttribute("note", note);
        }else{
            model.addAttribute("noteError", "Failed to show your Note!");
        }

        return "home#nav-notes";
    }
*/
}

