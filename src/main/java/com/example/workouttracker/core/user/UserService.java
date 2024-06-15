package com.example.workouttracker.core.user;

import com.example.workouttracker.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.openapitools.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public ResponseEntity<List<User>> getUsers(Integer page, Integer size) {
        return ResponseEntity.ok(userRepository.findAll(PageRequest.of(page, size)).map(userMapper::toDto).getContent());
    }

    public ResponseEntity<User> getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<User> getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<User> updateUser(String id, User user) {
        return userRepository.findById(UUID.fromString(id))
                .map(userEntity -> {
                    userEntity.setFirstName(user.getFirstName());
                    userEntity.setLastName(user.getLastName());
                    userEntity.setEmail(user.getEmail());
                    return ResponseEntity.ok(userMapper.toDto(userRepository.save(userEntity)));
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
