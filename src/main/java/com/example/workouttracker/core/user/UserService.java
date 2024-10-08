package com.example.workouttracker.core.user;

import com.example.model.User;
import com.example.model.UserCreate;
import com.example.model.UserDetails;
import com.example.workouttracker.core.exception.UserException;
import com.example.workouttracker.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> getUsers(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .map(userMapper::toDto).getContent();
    }

    public UserDetails getUser(String userId) {
        try {
            return userRepository.findById(UUID.fromString(userId))
                    .map(userMapper::toDetailsDto)
                    .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));
        } catch (UserException e) {
            log.error("User not found: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while retrieving the user: {}", e.getMessage(), e);
            throw e;
        }
    }

    public User getCurrentUser() {
        try {
            UserEntity user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));

            return userMapper.toDto(user);
        } catch (UserException e) {
            log.error("Current user not found: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while retrieving the current user: {}", e.getMessage(), e);
            throw e;
        }
    }

    public User updateCurrentUser(UserCreate userCreate) {
        try {
            UserEntity existingUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));

            existingUser.setFirstName(userCreate.getFirstName());
            existingUser.setLastName(userCreate.getLastName());
            existingUser.setEmail(userCreate.getEmail());
            existingUser.setGenders(new HashSet<>(Collections.singleton(UserGender.valueOf(userCreate.getGender()))));
            existingUser.setAge(userCreate.getAge());
            existingUser.setHeight(userCreate.getHeight());

            userRepository.save(existingUser);
            return userMapper.toDto(existingUser);
        } catch (UserException e) {
            log.error("User update failed: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating the user: {}", e.getMessage(), e);
            throw e;
        }
    }

    public UserDetails updateUser(String id, UserCreate userCreate) {
        try {
            UserEntity existingUser = userRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));

            existingUser.setFirstName(userCreate.getFirstName());
            existingUser.setLastName(userCreate.getLastName());
            existingUser.setEmail(userCreate.getEmail());
            existingUser.setGenders(new HashSet<>(Collections.singleton(UserGender.valueOf(userCreate.getGender()))));
            existingUser.setAge(userCreate.getAge());
            existingUser.setHeight(userCreate.getHeight());

            userRepository.save(existingUser);
            return userMapper.toDetailsDto(existingUser);
        } catch (UserException e) {
            log.error("User not found for update: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating the user: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteUser(String userId) {
        try {
            UserEntity user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new UserException(UserException.FailReason.NOT_FOUND));
            userRepository.delete(user);
        } catch (UserException e) {
            log.error("User not found for deletion: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting the user: {}", e.getMessage(), e);
            throw e;
        }
    }
}
