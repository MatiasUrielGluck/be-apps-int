package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.auth.*;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.enums.KycStatusEnum;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.exception.UserAlreadyExistsException;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthService interface.
 * Provides methods for handling authentication and authorization.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new customer.
     *
     * @param request the signup request data transfer object
     * @return the signup response data transfer object
     * @throws UserAlreadyExistsException if a customer with the given email already exists
     * @throws BadRequestException if the password is not provided
     */
    public SignupResponseDTO signup(SignupRequestDTO request) {
        if (customerRepository.existsByEmail(request.getEmail())) throw new UserAlreadyExistsException(request.getEmail());
        if (request.getPassword() == null) throw new BadRequestException("Password is required.");

        Customer newUser = Customer.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .kycStatus(KycStatusEnum.BASIC_KYC)
                .isAdmin(false)
                .build();

        customerRepository.save(newUser);

        return SignupResponseDTO.builder()
                .message("The user was created.")
                .build();
    }

    /**
     * Authenticates a customer and generates a JWT token.
     *
     * @param request the login request data transfer object
     * @return the login response data transfer object
     */
    public LoginResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(customer);
        return LoginResponseDTO.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
    }

    /**
     * Retrieves the authenticated customer.
     *
     * @return the authenticated customer
     */
    public Customer getAuthenticatedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Customer) authentication.getPrincipal();
    }

    /**
     * Retrieves the information of the authenticated customer.
     *
     * @return the customer information data transfer object
     */
    public CustomerInfoDTO getCustomerInfo() {
        return getAuthenticatedCustomer().toDto();
    }
}
