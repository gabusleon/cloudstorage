package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FilesService filesService;
    private final UserService userService;

    public HomeController(FilesService filesService, UserService userService) {
        this.filesService = filesService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication){
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null) {
            model.addAttribute("allFilesByUser", filesService.getUserFiles(userLogged.getUserid()));
            return "home";
        }else{
            return "login";
        }
    }
}
