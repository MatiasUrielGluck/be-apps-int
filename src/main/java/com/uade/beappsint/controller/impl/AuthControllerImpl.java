package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.AuthController;
import com.uade.beappsint.dto.auth.*;
import com.uade.beappsint.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Implementation of the AuthController interface.
 * Provides endpoints for user signup, login, and retrieving customer information.
 */
@RequestMapping("/auth")
@RestController
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    /**
     * Constructs a new AuthControllerImpl with the specified AuthService.
     *
     * @param authService the authentication service to use
     */
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Signs up a new user.
     *
     * @param request the signup request data transfer object
     * @return a ResponseEntity containing the signup response data transfer object
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Validated SignupRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(request));
    }

    /**
     * Logs in an existing user.
     *
     * @param loginRequestDTO the login request data transfer object
     * @return a ResponseEntity containing the login response data transfer object
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginRequestDTO loginRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(loginRequestDTO));
    }

    /**
     * Retrieves information about the authenticated customer.
     *
     * @return a ResponseEntity containing the customer information data transfer object
     */
    @GetMapping("/info")
    public ResponseEntity<CustomerInfoDTO> customerInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.getCustomerInfo());
    }

    /**
     * A public endpoint for testing purposes.
     *
     * @return a string message for testing the public endpoint
     */
    @PostMapping("/test")
    public String publicEndpointTest() {
        return "Hello World from UADE";
    }
}
