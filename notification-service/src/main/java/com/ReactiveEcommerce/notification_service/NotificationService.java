package com.ReactiveEcommerce.notification_service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to,String subject,String body) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("beimnetworku101@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);


    }
    public void sendOrderPlacementNotification(String to) {
        String subject = "Order Confirmation - Order #";
        String body = "Dear Customer,\n\nThank you for your order!"+
                ". We are processing it and will update you once it's shipped.\n\nBest regards,\nReactive Ecommerce Team";
        sendEmail(to, subject, body);
    }

    // Method for user registration notification
    public void sendUserRegistrationNotification(String to) {
        String subject = "Welcome to Reactive Ecommerce ";// username + "!";
        String body = "Dear " + "user" + ",\n\nWelcome to Reactive Ecommerce! We're excited to have you on board." +
                " Feel free to explore our platform and enjoy shopping.\n\nBest regards,\nReactive Ecommerce Team";
        sendEmail(to, subject, body);
    }

    // Method for sign-up confirmation notification
    public void sendSignUpConfirmation(String to) {
        String subject = "Sign-Up Confirmation";
        String body = "Dear User,\n\nThank you for signing up for Reactive Ecommerce! Your account has been successfully created." +
                " If you have any questions, feel free to reach out to us.\n\nBest regards,\nReactive Ecommerce Team";
        sendEmail(to, subject, body);
    }
}

