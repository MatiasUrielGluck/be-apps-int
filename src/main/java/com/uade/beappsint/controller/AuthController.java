package com.uade.beappsint.controller;

import com.uade.beappsint.dto.auth.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

/**
 * Interface for authentication-related endpoints.
 * Provides methods for user signup, login, and retrieving customer information.
 */
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public interface AuthController {

    /**
     * Signs up a new user.
     *
     * @param request the signup request data transfer object
     * @return a ResponseEntity containing the signup response data transfer object
     */
    @Operation(summary = "Sign up a new user", description = "Creates a new user in the system")
    ResponseEntity<SignupResponseDTO> signup(SignupRequestDTO request);

    /**
     * Logs in an existing user.
     *
     * @param loginRequestDTO the login request data transfer object
     * @return a ResponseEntity containing the login response data transfer object
     */
    @Operation(summary = "Log in an existing user", description = "Authenticates a user and returns a token")
    ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO);

    /**
     * Retrieves information about the authenticated customer.
     *
     * @return a ResponseEntity containing the customer information data transfer object
     */
    @Operation(summary = "Get customer information", description = "Retrieves information about the authenticated customer")
    ResponseEntity<CustomerInfoDTO> customerInfo();

    /**
     * A public endpoint for testing purposes.
     *
     * @return a string message for testing the public endpoint
     */
    @Operation(summary = "Test public endpoint", description = "A public endpoint for testing purposes")
    String publicEndpointTest();
}
