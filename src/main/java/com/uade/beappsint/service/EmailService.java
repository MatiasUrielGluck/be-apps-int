package com.uade.beappsint.service;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendEmailFromTemplate(String to, String subject, String templateName, Map<String, String> replacements) throws MessagingException, IOException;
}
