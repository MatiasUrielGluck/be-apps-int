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
public class PasswordChangeServiceImpl {

    private final EmailService emailService;
    private final AuthService authService;

    public void sendPasswordChangeConfirmationEmail(String toEmail) throws IOException, MessagingException {
        Customer customer = authService.getAuthenticatedCustomer();
        // Reemplazos para la plantilla
        Map<String, String> replacements = new HashMap<>();

        // Enviar el correo
        emailService.sendEmailFromTemplate(
                toEmail,
                "Cambio de contraseña exitoso",
                "PasswordChangeSuccess",
                replacements
        );
    }
}

