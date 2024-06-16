package com.example.workouttracker.email;

import com.example.workouttracker.core.user.UserEntity;
import com.example.workouttracker.core.user.UserRepository;
import com.example.workouttracker.security.JwtService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.model.UserPasswordReset;
import org.openapitools.api.EmailApi;

@RestController
@AllArgsConstructor
public class EmailController implements EmailApi {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/request-password-reset")
    public ResponseEntity<EmailResponse> requestPasswordReset(@RequestBody @Valid UserPasswordReset userPasswordReset) throws MessagingException {
        emailService.sendPasswordResetEmail(userPasswordReset.getEmail());
        return ResponseEntity.ok(new EmailResponse("Password reset link sent to your email address."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<EmailResponse> resetPassword(@RequestParam("token") String token, @RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        UserEntity userDetails = userRepository.findByEmail(jwtService.extractEmail(token))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new RuntimeException("Token has expired or is invalid");
        }

        String email = jwtService.extractEmail(token);
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Resetting password for user: " + email);

        userEntity.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
        userRepository.save(userEntity);

        return ResponseEntity.ok(new EmailResponse("Password has been reset successfully."));
    }
}
