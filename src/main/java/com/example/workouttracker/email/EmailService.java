package com.example.workouttracker.email;

import com.example.workouttracker.config.security.service.JwtService;
import com.example.workouttracker.core.exception.UserException;
import com.example.workouttracker.core.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@AllArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Async
    public void sendPasswordResetEmail(String email) {
        try {
            userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));

            String token = jwtService.generatePasswordResetToken(email);
            String resetLink = "http://localhost:8080/reset-password?token=" + token;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setFrom("no-reply@workouttracker.com");
            helper.setSubject("Reset Password");

            String htmlContent = "<h1>WorkoutTracker</h1>" +
                    "<p>Click the link below to reset your password:</p>" +
                    "<a href=\"" + resetLink + "\">Reset Password</a>" +
                    "<p>If you didn't request a password reset, please ignore this email.</p>";
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Password reset email sent to {}", email);
        } catch (UserException e) {
            log.error("Failed to find user by email {}: {}", email, e.getMessage(), e);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", email, e.getMessage(), e);
        } catch (Exception e) {
            log.error("An unexpected error occurred while sending password reset email to {}: {}", email, e.getMessage(), e);
        }
    }
}