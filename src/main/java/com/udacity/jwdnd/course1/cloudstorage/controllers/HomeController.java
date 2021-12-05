package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
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
    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;

    public HomeController(FilesService filesService, NotesService notesService, UserService userService, CredentialsService credentialsService, EncryptionService encryptionService) {
        this.filesService = filesService;
        this.notesService = notesService;
        this.userService = userService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm") CredentialForm credentialForm, Model model, Authentication authentication){
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null) {
            if (model.getAttribute("activeTab") == null){
                model.addAttribute("activeTab", "files");
            }

            model.addAttribute("encryptionService", encryptionService);
            model.addAttribute("allFilesByUser", filesService.getUserFiles(userLogged.getUserid()));
            model.addAttribute("allNotesByUser", notesService.readNotes(userLogged.getUserid()));
            model.addAttribute("credentials", credentialsService.readCredentials(userLogged.getUserid()));

            return "home";
        }else{
            return "login";
        }
    }
}
