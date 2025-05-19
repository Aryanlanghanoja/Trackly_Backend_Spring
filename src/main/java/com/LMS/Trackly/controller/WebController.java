package com.LMS.Trackly.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "${frontend.url}", allowCredentials = "true")
public class WebController {

    @GetMapping("/")
    public String Default() {
        return "Welcome to the Trackly Lead Management API!";
    }
}
