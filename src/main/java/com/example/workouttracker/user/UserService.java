package com.example.workouttracker.user;

import com.example.workouttracker.dto.UserDto;
import com.example.workouttracker.mapper.UserMapper;
import lombok.AllArgsConstructor;
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

    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userMapper.toDto(userRepository.findAll()));
    }

    public ResponseEntity<UserDto> getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserDto> createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(RoleType.USER));
        UserDto newUser = userMapper.toDto(userRepository.save(user));

        return ResponseEntity.ok(newUser);
    }

    public ResponseEntity<UserDto> updateUser(String id, User user) {
        return userRepository.findById(UUID.fromString(id))
                .map(userToUpdate -> {
                    userToUpdate.setUsername(user.getUsername());
                    userToUpdate.setEmail(user.getEmail());
                    userToUpdate.setFirstName(user.getFirstName());
                    userToUpdate.setLastName(user.getLastName());
                    userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
                    userToUpdate.setRoles(user.getRoles());
                    UserDto updatedUser = userMapper.toDto(userRepository.save(userToUpdate));
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
