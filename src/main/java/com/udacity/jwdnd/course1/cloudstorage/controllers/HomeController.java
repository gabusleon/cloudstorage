package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FilesService filesService;
    private final NotesService notesService;
    private final UserService userService;

    public HomeController(FilesService filesService, NotesService notesService, UserService userService) {
        this.filesService = filesService;
        this.notesService = notesService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, Model model, Authentication authentication){
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null) {
            if (model.getAttribute("activeTab") == null){
                model.addAttribute("activeTab", "files");
            }

            model.addAttribute("allFilesByUser", filesService.getUserFiles(userLogged.getUserid()));
            model.addAttribute("allNotesByUser", notesService.readNotes(userLogged.getUserid()));
            return "home";
        }else{
            return "login";
        }
    }
}
