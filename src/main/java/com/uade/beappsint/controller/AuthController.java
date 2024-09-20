package com.uade.beappsint.controller;

import com.uade.beappsint.dto.auth.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Endpoints for user authentication")
public interface AuthController {
    @Operation(summary = "Sign up a new user", description = "Creates a new user in the system")
    ResponseEntity<SignupResponseDTO> signup(SignupRequestDTO request);

    @Operation(summary = "Log in an existing user", description = "Authenticates a user and returns a token")
    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO);

    @Operation(summary = "Get customer information", description = "Retrieves information about the authenticated customer")
    ResponseEntity<CustomerInfoDTO> customerInfo();

    @Operation(summary = "Test public endpoint", description = "A public endpoint for testing purposes")
    String publicEndpointTest();
}
