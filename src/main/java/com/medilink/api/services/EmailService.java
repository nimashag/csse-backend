package com.medilink.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending email notifications to doctors and hospitals.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    // Injecting the custom properties
    @Value("${app.mode}")
    private String appMode; // The current mode of the application (e.g., "test" or "production")

    @Value("${app.test.email}")
    private String testEmail; // The test email address to use in testing mode

    @Value("${app.email.enabled}")
    private boolean isEmailEnabled; // Flag indicating whether email sending is enabled

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a welcome email to a newly registered doctor.
     * @param toEmail The email address of the doctor.
     * @param doctorName The name of the doctor.
     * @param password The password for the doctor's account.
     */
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

    /**
     * Sends a welcome email to a newly registered hospital.
     * @param toEmail The email address of the hospital.
     * @param hospitalName The name of the hospital.
     */
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
