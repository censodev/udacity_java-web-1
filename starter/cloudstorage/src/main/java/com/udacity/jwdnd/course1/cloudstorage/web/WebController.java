package com.udacity.jwdnd.course1.cloudstorage.web;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class WebController {
    private final UserService userService;
    private final FileService fileService;

    public WebController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("signup")
    public String showSignup() {
        return "signup";
    }

    @PostMapping("signup")
    public String signup(@ModelAttribute User form) {
        try {
            userService.signup(form);
            return "redirect:/signup?success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup?fail";
        }
    }

    @GetMapping("home")
    public String home(Authentication auth, Model model) {
        var userId = ((User) auth.getPrincipal()).getUserid();
        var files = fileService.findByUserId(userId);

        model.addAttribute("files", files);
        return "home";
    }

    @PostMapping("file/upload")
    public String uploadFile(@RequestParam MultipartFile fileUpload, Authentication auth) throws IOException {
        var metadata = new File();
        metadata.setFilename(fileUpload.getOriginalFilename());
        metadata.setContenttype(fileUpload.getContentType());
        metadata.setFilesize(String.valueOf(fileUpload.getSize()));
        metadata.setUserid(((User) auth.getPrincipal()).getUserid());
        fileService.save(fileUpload.getInputStream(), metadata);
        return "redirect:/home";
    }

    @GetMapping("file/view/{id}")
    @ResponseBody
    public Resource viewFile(@PathVariable int id) {
        return fileService.load(id);
    }

    @GetMapping("file/delete/{id}")
    public String deleteFile(@PathVariable int id) {
        fileService.delete(id);
        return "redirect:/home";
    }
}
