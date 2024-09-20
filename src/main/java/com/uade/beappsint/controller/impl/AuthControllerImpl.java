package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.AuthController;
import com.uade.beappsint.dto.auth.*;
import com.uade.beappsint.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Validated SignupRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginRequestDTO loginRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(loginRequestDTO));
    }

    @GetMapping("/info")
    public ResponseEntity<CustomerInfoDTO> customerInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.getCustomerInfo());
    }

    @PostMapping("/test")
    public String publicEndpointTest() {
        return "Hello World from UADE";
    }
}
