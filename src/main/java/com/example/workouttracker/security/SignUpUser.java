package com.example.workouttracker.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpUser {
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
