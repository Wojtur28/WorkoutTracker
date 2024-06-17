package com.example.workouttracker.core.user;

import com.example.workouttracker.core.exception.UserException;
import lombok.AllArgsConstructor;
import org.openapitools.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.openapitools.api.UserApi;
import org.openapitools.model.ErrorResponse;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<List<User>> getUsers(@RequestParam Integer page,
                                               @RequestParam Integer size) {
        List<User> users = userService.getUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

/*    @Override
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }*/

    @Override
    public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleException(UserException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        switch (e.getFailReason()) {
            case NOT_FOUND:
                errorResponse.setCode("USER_NOT_FOUND");
                errorResponse.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.toString());
            case NOT_UNIQUE_EMAIL:
                errorResponse.setCode("EMAIL_NOT_UNIQUE");
                errorResponse.setMessage("Email is not unique");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse.toString());
            case DOES_NOT_HAVE_PROPER_ROLE:
            case DOES_NOT_HAVE_ROLE:
            case ROLE_NOT_FOUND:
                errorResponse.setCode("ROLE_ERROR");
                errorResponse.setMessage("Role error");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse.toString());
            default:
                errorResponse.setCode("INTERNAL_SERVER_ERROR");
                errorResponse.setMessage("Internal server error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }
}
