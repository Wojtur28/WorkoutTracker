package com.example.workouttracker.user;

import com.example.workouttracker.dto.UserDto;
import com.example.workouttracker.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userMapper.toDto(userRepository.findAll()));
    }

    public ResponseEntity<UserDto> getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<User> createUser(User user) {
        User newUser = userRepository.save(user);
        newUser.setRoles(Collections.singleton(RoleType.USER));
        return ResponseEntity.ok(newUser);
    }

    public ResponseEntity<User> updateUser(String id, User user) {
        return userRepository.findById(UUID.fromString(id))
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setRoles(user.getRoles());
                    userRepository.save(existingUser);
                    return ResponseEntity.ok(existingUser);
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