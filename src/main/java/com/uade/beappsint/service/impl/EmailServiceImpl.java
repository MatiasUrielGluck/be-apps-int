package com.uade.beappsint.service.impl;

import com.uade.beappsint.service.EmailService;
import com.uade.beappsint.service.FileService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final FileService fileService;

    @Value("${EMAIL_USERNAME}")
    private String fromEmail;

    private final List<String> allowedEmails = new ArrayList<>(){{
        add("matiasugluck@gmail.com");
        add("tomasignacioalv@hotmail.com");
    }};

    @Override
    public void sendEmail(String to, String subject, String body) {
    }


    public void sendEmailFromTemplate(String to, String subject, String templateName, Map<String, String> replacements) throws MessagingException, IOException {
        if (!allowedEmails.contains(to)) return;

        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(fromEmail);

        String htmlTemplate = fileService.readFileFromResources("/templates/" + templateName + ".html");

        // Replace placeholders in the HTML template with dynamic values
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            htmlTemplate = htmlTemplate.replace("${" + entry.getKey() + "}", entry.getValue());
        }

        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
