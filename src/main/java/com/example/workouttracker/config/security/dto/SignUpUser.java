package com.example.workouttracker.config.security.dto;

import com.example.workouttracker.core.user.UserGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUser {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Boolean isTermsAndConditionsAccepted;
    private Set<UserGender> genders;

}
