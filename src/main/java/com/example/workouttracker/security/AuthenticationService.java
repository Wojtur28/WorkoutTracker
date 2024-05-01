package com.example.workouttracker.security;

import com.example.workouttracker.user.RoleType;
import com.example.workouttracker.user.UserEntity;
import com.example.workouttracker.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserEntity signup(SignUpUser input) {

        userRepository.findByEmail(input.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("User with this email already exists");
                });
        UserEntity user = new UserEntity();
        user.setEmail(input.getEmail());
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRoles(Set.of(RoleType.USER));

        return userRepository.save(user);
    }

    public UserEntity authenticate(SignInUser input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
