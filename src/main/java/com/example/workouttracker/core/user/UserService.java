package com.example.workouttracker.core.user;

import com.example.workouttracker.core.exception.UserException;
import com.example.workouttracker.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.openapitools.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<User> getUsers(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .map(userMapper::toDto).getContent();
    }

    public User getUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));
    }

    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));
    }

    public User updateUser(String id, User user) {
        UserEntity existingUser = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        userRepository.save(existingUser);
        return userMapper.toDto(existingUser);
    }

    public void deleteUser(String userId) {
        UserEntity user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));
        userRepository.delete(user);
    }

}
