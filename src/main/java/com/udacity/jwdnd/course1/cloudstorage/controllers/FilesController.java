package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class FilesController {
    private final FilesService filesService;
    private final UserService userService;

    public FilesController(FilesService filesService, UserService userService) {
        this.filesService = filesService;
        this.userService = userService;
    }
/*
    @GetMapping("/files")
    public String uploadFilePageView(Model model, Authentication authentication){
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null) {
            model.addAttribute("allFilesByUser", filesService.getUserFiles(userLogged.getUserid()));
            return "home";
        }else{
            return "login";
        }
    }
*/

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model, RedirectAttributes redirectAttributes){
        String uploadError = null;
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null) {
            try {
                if (fileUpload.isEmpty()) {
                    uploadError = "Failed to store empty file.";
                }
                if (uploadError == null) {
                    if (userLogged != null) {
                        if (filesService.isUserFilenameAvailable(fileUpload.getOriginalFilename(), userLogged.getUserid())) {
                            InputStream inputStream = fileUpload.getInputStream();
                            Files newFileData = new Files(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), String.valueOf(fileUpload.getSize()), userLogged.getUserid(), inputStream.readAllBytes());
                            int rowsAdded = filesService.uploadUserFile(fileUpload, newFileData);
                            if (rowsAdded < 0) {
                                uploadError = "Failed to upload file!";
                            }
                        } else {
                            uploadError = "The file is already uploaded!";
                        }
                    }
                }
            } catch (IOException e) {
                uploadError = "Failed to upload file!";
            }
        }else{
            return "login";
        }
        if(uploadError == null){
            model.addAttribute("success", "You successfully upload your file!");
        }else{
            model.addAttribute("error", uploadError);
        }
        //model.addAttribute("allFilesByUser", filesService.getUserFiles(userLogged.getUserid()));
        redirectAttributes.addFlashAttribute("activeTab", "files");

        return "redirect:/home";
    }

    @GetMapping("/file-download/{fileid}")
    public ResponseEntity<Resource> downlodFile(@PathVariable("fileid") String fileid, Model model, Authentication authentication){
        Users userLogged = userService.getUser(authentication.getName());
        if(userLogged != null) {
            //model.addAttribute("allFilesByUser", filesService.getUserFiles(userLogged.getUserid()));
            Files file = filesService.downloadUserFile(Integer.parseInt(fileid));
            if(file != null) {
                ByteArrayResource resource = new ByteArrayResource(file.getFiledata());

                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");


                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(Long.parseLong(file.getFilesize()))
                        .contentType(MediaType.parseMediaType(file.getContenttype()))
                        .body(resource);
            }else{
                model.addAttribute("error", "Failed to view/download file!");

                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/home");
                return new ResponseEntity<Resource>(headers, HttpStatus.BAD_REQUEST);
            }
        }else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/login");
            return new ResponseEntity<Resource>(headers, HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/file-delete/{fileid}")
    public String deleteFile(@PathVariable("fileid") String fileid, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        Users userLogged = userService.getUser(authentication.getName());

        if(userLogged != null) {
            int rowsDeleted = filesService.deleteUserFile(Integer.parseInt(fileid));
            if(rowsDeleted < 0){
                model.addAttribute("error", "Failed to delete your file!");
            }else{
                model.addAttribute("success", "You successfully deleted your file!");
            }
            //model.addAttribute("allFilesByUser", filesService.getUserFiles(userLogged.getUserid()));
            redirectAttributes.addFlashAttribute("activeTab", "notes");

            return "redirect:/home";
        }else{
            return "login";
        }

    }

}
