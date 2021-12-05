package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(){
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute Users user, Model model){
        String signupErrorText = null;

        if(!userService.isUsernameAvailable(user.getUsername())){
             signupErrorText = "El nombre de usuario ya existe!";
        }

        if(signupErrorText == null){
            int rowsAdded = userService.createUser(user);
            if(rowsAdded < 0){
                signupErrorText = "Ocurrio un error al registrar el nuevo usuario. Inténtelo nuevamente!";
            }
        }

        if(signupErrorText == null){
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", true);
            model.addAttribute("signupErrorText", signupErrorText);

        }

        return "signup";
    }
}
