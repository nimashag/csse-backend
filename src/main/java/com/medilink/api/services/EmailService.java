package com.medilink.api.services;

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

    @Value("${app.email.enabled}")
    private boolean isEmailEnabled;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendDoctorWelcomeEmail(String toEmail, String doctorName, String password) {
        if (!isEmailEnabled) {
            logger.warn("[EMAIL] Email service is disabled. Skipping email send.");
            return; // Do not send the email if it's disabled in properties
        }

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

    public void sendHospitalWelcomeEmail(String toEmail, String hospitalName) {
        if (!isEmailEnabled) {
            logger.warn("[EMAIL] Email service is disabled. Skipping email send.");
            return; // Do not send the email if it's disabled in properties
        }

        SimpleMailMessage message = new SimpleMailMessage();

        // If app is in testing mode, use the test email, otherwise use the provided email
        if ("test".equalsIgnoreCase(appMode)) {
            toEmail = testEmail;
        }

        message.setTo(toEmail);
        message.setSubject("Welcome to MediLink!");
        message.setText(String.format(
                "Dear %s,\n\nWelcome to MediLink!\n\nThank you for registering your hospital with us!\n\nBest regards,\nThe MediLink Team",
                hospitalName
        ));

        logger.info("[EMAIL] sending welcome email to hospital: {}, email: {}", hospitalName, toEmail);
        mailSender.send(message);
    }
}
