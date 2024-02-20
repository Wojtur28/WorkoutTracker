package com.example.workouttracker.user;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.userMeasurement.UserMeasurement;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AuditBase {

    @NotNull
    @Length(min = 3, max = 32, message = "Username must be between 3 and 32 characters long")
    private String username;

    @NotNull
    @Length(min = 3, max = 32, message = "Email must be between 3 and 32 characters long")
    private String email;

    @NotNull
    @Length(min = 3, max = 32, message = "First name must be between 3 and 32 characters long")
    private String firstName;

    @NotNull
    @Length(min = 3, max = 32, message = "Last name must be between 3 and 32 characters long")
    private String lastName;

    @NotNull
    @Length(min = 3, max = 32, message = "Password must be between 3 and 32 characters long")
    private String password;

    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_name", nullable = false)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<UserMeasurement> userMeasurements;

}
