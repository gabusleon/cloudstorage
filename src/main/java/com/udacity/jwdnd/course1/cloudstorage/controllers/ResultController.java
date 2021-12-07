package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

@Controller
public class ResultController {

    @GetMapping("/result")
    public String showResults(Model model){
        String activeTab = model.getAttribute("activeTab").toString();

        return "result";
    }

    @GetMapping("/result/{activeTab}")
    public String redirectHome(@PathVariable("activeTab") String activeTab, Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("activeTab", activeTab);

        return "redirect:/home";
    }
/*
    @GetMapping("/result/download")
    public ResponseEntity<Resource> donwloadFile(Model model){
        String activeTab = model.getAttribute("activeTab").toString();
        Files file = (Files) model.getAttribute("file");

        ByteArrayResource resource = new ByteArrayResource(file.getFiledata());

        return ResponseEntity.ok()
                .contentLength(Long.parseLong(file.getFilesize()))
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFilename() + "\"")
                .body(resource);
    }
*/
}
