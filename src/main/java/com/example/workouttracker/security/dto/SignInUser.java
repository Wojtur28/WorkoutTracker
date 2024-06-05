package com.example.workouttracker.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInUser {
    private String email;
    private String password;
}
