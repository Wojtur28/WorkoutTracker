package com.example.workouttracker.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setFrom("sandbox.smtp.mailtrap.io");
        message.setSubject("Request password reset");

        String htmlContent = "<h1>WorkoutTracker</h1>" +
                "<p>Click link below to reset your password.</p>" +
                "<a href> link </a href>" +
                "<p>If you didn't want reset your password just ignore this email";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }



}
