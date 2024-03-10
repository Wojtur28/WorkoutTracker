package com.example.workouttracker.user;

import com.example.workouttracker.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        return userService.deleteUser(userId);
    }
}
