package com.example.workouttracker.security;

import com.example.workouttracker.core.user.UserGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SignUpUser {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isTermsAndConditionsAccepted;
    private Set<UserGender> genders;

}
