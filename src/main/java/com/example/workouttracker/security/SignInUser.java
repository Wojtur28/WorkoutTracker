package com.example.workouttracker.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInUser {
    private String email;
    private String password;
}
