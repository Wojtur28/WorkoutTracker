package com.example.workouttracker.core.user;

import lombok.AllArgsConstructor;
import org.openapitools.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.openapitools.api.UserApi;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<List<User>> getUsers(@RequestParam Integer page,
                                               @RequestParam Integer size) {
        return userService.getUsers(page, size);
    }

    @Override
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @Override
    public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        return userService.deleteUser(userId);
    }
}
