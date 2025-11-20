package com.example.jwtjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping("/secured")
    public String secured() {
        return "secured";
    }

    @GetMapping("/unsecured")
    public String unsecured() {
        return "unsecured";
    }
}
