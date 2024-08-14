package com.uade.beappsint.controller;

import com.uade.beappsint.dto.auth.*;
import com.uade.beappsint.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoints for user authentication")
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Sign up a new user", description = "Creates a new user in the system")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Validated SignupRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(request));
    }

    @Operation(summary = "Log in an existing user", description = "Authenticates a user and returns a token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginRequestDTO loginRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(loginRequestDTO));
    }

    @Operation(summary = "Get customer information", description = "Retrieves information about the authenticated customer")
    @GetMapping("/info")
    public ResponseEntity<CustomerInfoDTO> customerInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.getCustomerInfo());
    }

    @Operation(summary = "Test public endpoint", description = "A public endpoint for testing purposes")
    @PostMapping("/test")
    public String publicEndpointTest() {
        return "Hello World from UADE";
    }
}
