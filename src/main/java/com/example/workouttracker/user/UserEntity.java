package com.example.workouttracker.user;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.training.TrainingEntity;
import com.example.workouttracker.userMeasurement.UserMeasurementEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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


    @NotNull
    @NotBlank
    @Length(min = 3, max = 32, message = "Email must be between 3 and 32 characters long")
    @Column(unique = true)
    private String email;

    @NotNull
    @Length(min = 3, max = 32, message = "First name must be between 3 and 32 characters long")
    private String firstName;

    @NotNull
    @Length(min = 3, max = 32, message = "Last name must be between 3 and 32 characters long")
    private String lastName;

    @NotNull
    @Length(min = 3, max = 255, message = "Password must be between 3 and 32 characters long")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TrainingEntity> trainings;

    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_name", nullable = false)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
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
}
