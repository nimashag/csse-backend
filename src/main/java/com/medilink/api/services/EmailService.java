package com.medilink.api.services;

import com.medilink.api.controllers.DoctorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    // Injecting the custom properties
    @Value("${app.mode}")
    private String appMode;

    @Value("${app.test.email}")
    private String testEmail;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(String toEmail, String doctorName, String password) {
        SimpleMailMessage message = new SimpleMailMessage();

        // If app is in testing mode, use the test email, otherwise use the provided email
        if ("test".equalsIgnoreCase(appMode)) {
            toEmail = testEmail;
        }

        message.setTo(toEmail);
        message.setSubject("Welcome to MediLink!");
        message.setText(String.format(
                "Dear %s,\n\nWelcome to MediLink!\n\nYou can log into our system using the following credentials:\n\nEmail: %s\nPassword: %s\n\nBest regards,\nThe MediLink Team",
                doctorName, toEmail, password
        ));

        logger.info("[EMAIL] sending welcome email to: {}, email: {}", doctorName, toEmail);
        mailSender.send(message);
    }
}
