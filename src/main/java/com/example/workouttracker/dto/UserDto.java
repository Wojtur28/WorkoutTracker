package com.example.workouttracker.dto;

import com.example.workouttracker.user.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<RoleType> roles;
}
