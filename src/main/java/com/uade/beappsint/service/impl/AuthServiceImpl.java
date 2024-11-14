package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.GenericResponseDTO;
import com.uade.beappsint.dto.auth.*;
import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.enums.KycStatusEnum;
import com.uade.beappsint.enums.ThemeEnum;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.exception.UserAlreadyExistsException;
import com.uade.beappsint.repository.CustomerRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.EmailService;
import com.uade.beappsint.service.JwtService;
import com.uade.beappsint.utils.CommonUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    private void createAndSendVerificationCode(Customer customer) {
        int MAX_ITERATOR = 500;
        int iterator = 0;
        boolean isRepeated = true;
        String verificationCode;

        do {
            verificationCode = CommonUtilities.generateVerificationCode();

            Optional<Customer> customerOptional = customerRepository.findByVerificationCode(verificationCode);
            if (customerOptional.isEmpty()) {
                isRepeated = false;
            }

            iterator++;
        } while (iterator < MAX_ITERATOR && isRepeated);

        if (isRepeated) return;

        customer.setVerificationCode(verificationCode);
        customerRepository.save(customer);

        // Send email
        Map<String, String> replacements = new HashMap<>();
        replacements.put("code", verificationCode);

        try {
            emailService.sendEmailFromTemplate(customer.getEmail(), "Código de verificación", "VerificationEmail", replacements);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public SignupResponseDTO signup(SignupRequestDTO request) {
        if (customerRepository.existsByEmail(request.getEmail())) throw new UserAlreadyExistsException(request.getEmail());
        if (request.getPassword() == null) throw new BadRequestException("Password is required.");

        Customer newUser = Customer.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .kycStatus(KycStatusEnum.BASIC_KYC)
                .isAdmin(false)
                .isEnabled(true)
                .theme(ThemeEnum.DARK)
                .verified(false)
                .build();

        Customer savedCustomer = customerRepository.save(newUser);

        createAndSendVerificationCode(savedCustomer);

        return SignupResponseDTO.builder()
                .message("The user was created.")
                .build();
    }

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

    public Customer getAuthenticatedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Customer) authentication.getPrincipal();
    }

    public CustomerInfoDTO getCustomerInfo() {
        return getAuthenticatedCustomer().toDto();
    }

    public GenericResponseDTO resendVerificationCode() {
        createAndSendVerificationCode(getAuthenticatedCustomer());
        return GenericResponseDTO.builder()
                .message("OK")
                .build();
    }

    public GenericResponseDTO verifyVerificationCode(VerificationCodeDTO request) {
        Customer customer = getAuthenticatedCustomer();

        if (customer.getVerified() != null && customer.getVerified()) {
            throw new BadRequestException("Verification was already done.");
        }

        if (!request.getVerificationCode().equals(customer.getVerificationCode())) {
            System.out.println(request.getVerificationCode() + customer.getVerificationCode());
            throw new BadRequestException("The verification code is invalid.");
        }

        customer.setVerified(true);
        customer.setVerificationCode("");
        customerRepository.save(customer);

        return GenericResponseDTO.builder()
                .message("OK")
                .build();
    }
}
