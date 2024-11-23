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
public class ShippingUpdateServiceImpl {

    private final EmailService emailService;
    private final AuthService authService;

    public void sendShippingUpdateEmail(String toEmail, String trackingNumber, String trackingLink) throws IOException, MessagingException {
        Customer customer = authService.getAuthenticatedCustomer();
        // Send email
        Map<String, String> replacements = new HashMap<>();

        replacements.put("trackingNumber", trackingNumber);
        replacements.put("trackingLink", trackingLink);

        emailService.sendEmailFromTemplate(
                customer.getEmail(),
                "Pedido en Camino #" + trackingNumber,
                "OrderStatus",
                replacements
        );

    }
}
