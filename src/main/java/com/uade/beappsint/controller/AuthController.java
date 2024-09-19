package com.uade.beappsint.controller;

import com.uade.beappsint.dto.auth.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Endpoints for user authentication")
@RequestMapping("/auth")
@RestController
public interface AuthController {
    @Operation(summary = "Sign up a new user", description = "Creates a new user in the system")
    @PostMapping("/signup")
    ResponseEntity<SignupResponseDTO> signup(@RequestBody @Validated SignupRequestDTO request);

    @Operation(summary = "Log in an existing user", description = "Authenticates a user and returns a token")
    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginRequestDTO loginRequestDTO);

    @Operation(summary = "Get customer information", description = "Retrieves information about the authenticated customer")
    @GetMapping("/info")
    ResponseEntity<CustomerInfoDTO> customerInfo();

    @Operation(summary = "Test public endpoint", description = "A public endpoint for testing purposes")
    @PostMapping("/test")
    String publicEndpointTest();
}
