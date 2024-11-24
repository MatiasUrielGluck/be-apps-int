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
public class AccountLockedServiceImpl {

    private final EmailService emailService;

    public void sendAccountLockedEmail(String toEmail, String unlockLink) throws IOException, MessagingException {
        // Reemplazos para la plantilla
        Map<String, String> replacements = new HashMap<>();
        replacements.put("unlockLink", unlockLink);

        // Enviar el correo
        emailService.sendEmailFromTemplate(
                toEmail,
                "Cuenta bloqueada: acción requerida",
                "AccountLocked",
                replacements
        );
    }
}
