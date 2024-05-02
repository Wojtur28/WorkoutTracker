package com.example.workouttracker.security;

import com.example.workouttracker.core.user.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserEntity> signup(@RequestBody SignUpUser signUpUser) {
        UserEntity registeredUser = authenticationService.signup(signUpUser);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signin(@RequestBody SignInUser signInUser) {
        UserEntity authenticatedUser = authenticationService.authenticate(signInUser);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        SignInResponse loginResponse = new SignInResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(Math.toIntExact(jwtService.getExpirationTime()));

        return ResponseEntity.ok(loginResponse);
    }
}