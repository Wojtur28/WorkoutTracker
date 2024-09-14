package com.example.workouttracker.config.security.controller;

import com.example.model.User;
import com.example.workouttracker.config.security.dto.SignInResponse;
import com.example.workouttracker.config.security.dto.SignInUser;
import com.example.workouttracker.config.security.dto.SignUpUser;
import com.example.workouttracker.config.security.service.AuthenticationService;
import com.example.workouttracker.config.security.service.JwtService;
import com.example.workouttracker.core.user.UserEntity;
import com.example.workouttracker.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @Valid SignUpUser signUpUser) {
        try {
            UserEntity registeredUser = authenticationService.signup(signUpUser);
            return ResponseEntity.ok(userMapper.toDto(registeredUser));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signin(@RequestBody @Valid SignInUser signInUser) {
        UserEntity authenticatedUser = authenticationService.authenticate(signInUser);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        SignInResponse loginResponse = new SignInResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(Math.toIntExact(jwtService.getExpirationTime()));

        return ResponseEntity.ok(loginResponse);
    }
}