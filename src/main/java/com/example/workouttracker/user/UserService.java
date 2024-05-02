package com.example.workouttracker.user;

import com.example.workouttracker.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.openapitools.model.User;
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

    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userMapper.toDto(userRepository.findAll()));
    }

    public ResponseEntity<User> getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<User> updateUser(String id, User user) {
        UserEntity userEntity = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        userEntity.setEmail(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        /*userEntity.setPassword(passwordEncoder.encode(user.getPassword()));*/
        //TODO: Probably we don't want let users to change their password but
        // we should add mechanism to send email with link to reset password
        userEntity.setRoles(Collections.singleton(RoleType.USER));

        return ResponseEntity.ok(userMapper.toDto(userRepository.save(userEntity)));
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
