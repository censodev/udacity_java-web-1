package com.udacity.jwdnd.course1.cloudstorage.web;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public WebController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
            form.setPassword(passwordEncoder.encode(form.getPassword()));
            userService.insert(form);
            return "redirect:/signup?success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signup?fail";
        }
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }
}
