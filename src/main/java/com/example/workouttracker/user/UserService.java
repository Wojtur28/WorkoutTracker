package com.example.workouttracker.user;

import com.example.workouttracker.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.openapitools.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userMapper.toDto(userRepository.findAll()));
    }

    public ResponseEntity<User> getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<User> createUser(UserEntity userEntity) {
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
        userEntity.setRoles(Collections.singleton(RoleType.USER));
        User newUser = userMapper.toDto(userRepository.save(userEntity));

        return ResponseEntity.ok(newUser);
    }

    public ResponseEntity<User> updateUser(String id, UserEntity userEntity) {
        return userRepository.findById(UUID.fromString(id))
                .map(userToUpdate -> {
                    userToUpdate.setUsername(userEntity.getUsername());
                    userToUpdate.setEmail(userEntity.getEmail());
                    userToUpdate.setFirstName(userEntity.getFirstName());
                    userToUpdate.setLastName(userEntity.getLastName());
                    userToUpdate.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                    userToUpdate.setRoles(userEntity.getRoles());
                    User updatedUser = userMapper.toDto(userRepository.save(userToUpdate));
                    return ResponseEntity.ok(updatedUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    public ResponseEntity<Void> deleteUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
