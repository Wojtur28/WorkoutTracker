package com.example.workouttracker.email;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.model.UserPasswordReset;
import org.openapitools.api.EmailApi;

@RestController
@AllArgsConstructor
public class EmailController implements EmailApi {

    private final EmailService emailService;

    @Override
    public ResponseEntity<Void> resetUserPassword(@RequestBody UserPasswordReset userPasswordReset) throws MessagingException {
        emailService.sendEmail(userPasswordReset.getEmail());
        return ResponseEntity.ok().build();
    }
}
