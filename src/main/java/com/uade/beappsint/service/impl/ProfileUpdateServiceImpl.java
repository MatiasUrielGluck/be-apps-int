package com.uade.beappsint.service.impl;

import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileUpdateServiceImpl {

    private final EmailService emailService;
    private final AuthService authService;

    public void sendProfileUpdateEmail(String toEmail, String profileLink) throws IOException, MessagingException {
        Customer customer = authService.getAuthenticatedCustomer();
        // Send email
        Map<String, String> replacements = new HashMap<>();

        replacements.put("profileLink", profileLink);

        emailService.sendEmailFromTemplate(
                customer.getEmail(),
                "Tu perfil ha sido actualizado",
                "ProfileUpdate",
                replacements
        );
    }
}
