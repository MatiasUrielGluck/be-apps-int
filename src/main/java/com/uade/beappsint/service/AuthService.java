package com.uade.beappsint.service;

import com.uade.beappsint.dto.auth.SignupRequestDTO;
import com.uade.beappsint.dto.auth.SignupResponseDTO;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.exception.UserAlreadyExistsException;
import com.uade.beappsint.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignupResponseDTO signup(SignupRequestDTO request) {
        if (customerRepository.existsByEmail(request.getEmail())) throw new UserAlreadyExistsException(request.getEmail());
        if (request.getPassword() == null) throw new BadRequestException("Password is required.");

        Customer newUser = Customer.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        customerRepository.save(newUser);

        return SignupResponseDTO.builder()
                .message("The user was created.")
                .build();
    }
}
