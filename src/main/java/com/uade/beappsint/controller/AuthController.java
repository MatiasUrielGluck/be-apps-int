package com.uade.beappsint.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @PostMapping("/login")
    public String privateEndpointTest() {
        return "You should not be seeing this...";
    }

    @PostMapping("/test")
    public String publicEndpointTest() {
        return "Hello World from UADE";
    }
}
