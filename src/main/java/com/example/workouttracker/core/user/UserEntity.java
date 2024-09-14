package com.example.workouttracker.core.user;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.training.TrainingEntity;
import com.example.workouttracker.core.userMeasurement.UserMeasurementEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AuditBase implements UserDetails {

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "The first name can't be blank")
    @Length(min = 3, max = 32, message = "First name must be between 3 and 32 characters long")
    private String firstName;

    @NotBlank(message = "The last name can't be blank")
    @Length(min = 3, max = 32, message = "Last name must be between 3 and 32 characters long")
    private String lastName;

    @NotBlank
    @Length(min = 3, max = 255, message = "Password must be between 3 and 32 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,40}$", message = """
            Password must be:\
            Have 8 characters or more (8-40)
            Include a capital letter
            Use at least one lowercase letter
            Consists of at least one digit
            Need to have one special symbol (i.e., @, #, $, %, etc.)
            Doesn’t contain space, tab, etc.""")
    private String password;

    @NotNull(message = "Terms must be accepted")
    private Boolean isTermsAndConditionsAccepted;

    @NotBlank(message = "The age can't be blank")
    private int age;

    @NotBlank(message = "The height can't be blank")
    private int height;

    @ElementCollection(targetClass = UserGender.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_gender", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    Set<UserGender> genders;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TrainingEntity> trainings;

    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserMeasurementEntity> userMeasurement;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "genders=" + genders +
                ", height=" + height +
                ", age=" + age +
                ", isTermsAndConditionsAccepted=" + isTermsAndConditionsAccepted +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
