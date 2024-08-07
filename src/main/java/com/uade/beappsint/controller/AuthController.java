package com.uade.beappsint.controller;

import com.uade.beappsint.dto.auth.SignupRequestDTO;
import com.uade.beappsint.dto.auth.SignupResponseDTO;
import com.uade.beappsint.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Validated SignupRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(request));
    }

    @PostMapping("/login")
    public String privateEndpointTest() {
        return "You should not be seeing this...";
    }

    @PostMapping("/test")
    public String publicEndpointTest() {
        return "Hello World from UADE";
    }
}
