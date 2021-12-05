package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.model.forms.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
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
public class CredentialsController {
    private final CredentialsService credentialsService;
    private final UserService userService;

    public CredentialsController(CredentialsService credentialsService, UserService userService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    @PostMapping("/save-credential")
    public String saveCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        String credentialError = null;
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null){
            Credentials credential = new Credentials();
            credential.setUrl(credentialForm.getUrl());
            credential.setUsername(credentialForm.getUsername());
            credential.setPassword(credentialForm.getPassword());
            credential.setUserid(userLogged.getUserid());

            if(credentialForm.getCredentialid() != null){
                credential.setCredentialid(credentialForm.getCredentialid());
                int updatedRows = credentialsService.updateCredential(credential);
                if(updatedRows < 0){
                    credentialError = "Failed to updated cedential!";
                }
            }else{
                int rowsAdded = credentialsService.createCredential(credential);
                if(rowsAdded < 0){
                    credentialError = "Failed to create credential!";
                }
            }
        }else{
            return "login";
        }

        if(credentialError == null){
            model.addAttribute("credentialSuccess", "You successfully saved your Credential!");
        }else{
            model.addAttribute("credentialError", credentialError);
        }
        //model.addAttribute("credentials", credentialsService.readCredentials(userLogged.getUserid()));
        redirectAttributes.addFlashAttribute("activeTab", "creds");

        return "redirect:/home";
    }

    @GetMapping("/delete-credential/{credentialid}")
    public String deleteCredential(@PathVariable("credentialid") String credentialid, @ModelAttribute("credentialForm") CredentialForm credentialForm, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        Users userLogged = userService.getUser(authentication.getName());

        if (userLogged != null) {
            int rowsDeleted = credentialsService.deleteCredential(Integer.parseInt(credentialid));
            if (rowsDeleted < 0) {
                model.addAttribute("credentialError", "Failed to delete your credential!");
            } else {
                model.addAttribute("credentialSuccess", "You successfully deleted your Credential!");
            }

            //model.addAttribute("credentials", credentialsService.readCredentials(userLogged.getUserid()));
            redirectAttributes.addFlashAttribute("activeTab", "creds");

            return "redirect:/home";
        } else {
            return "login";
        }
    }
}
