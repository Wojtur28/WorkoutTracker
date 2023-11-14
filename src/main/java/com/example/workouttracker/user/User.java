package com.example.workouttracker.user;


import com.example.workouttracker.AuditBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@Entity(name = "user")
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

}
