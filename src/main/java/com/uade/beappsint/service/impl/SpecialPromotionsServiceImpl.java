package com.uade.beappsint.service.impl;

import com.uade.beappsint.entity.Customer;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpecialPromotionsServiceImpl {

    private final EmailService emailService;

    public void sendSpecialPromotionsEmail(String toEmail, List<String> promotions, String promoLink) throws IOException, MessagingException {
        // Convertir lista de promociones en HTML
        StringBuilder promoHtml = new StringBuilder();
        promotions.forEach(promo -> promoHtml.append("<li>").append(promo).append("</li>"));

        // Reemplazos para la plantilla
        Map<String, String> replacements = new HashMap<>();
        replacements.put("promotions", promoHtml.toString());
        replacements.put("promoLink", promoLink);

        // Enviar el correo
        emailService.sendEmailFromTemplate(
                toEmail,
                "Promociones Exclusivas para Ti",
                "SpecialPromotions",
                replacements
        );

    }
}

