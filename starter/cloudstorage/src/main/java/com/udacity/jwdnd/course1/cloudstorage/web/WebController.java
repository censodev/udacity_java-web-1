package com.udacity.jwdnd.course1.cloudstorage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }
}
