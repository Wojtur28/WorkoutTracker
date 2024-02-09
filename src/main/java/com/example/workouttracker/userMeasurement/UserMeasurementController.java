package com.example.workouttracker.userMeasurement;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/measurement")
public class UserMeasurementController {

    private final UserMeasurementService userMeasurementService;

    @GetMapping
    public ResponseEntity<List<UserMeasurement>> getUserMeasurements() {
        return userMeasurementService.getUserMeasurements();
    }

    @GetMapping("/{userMeasurementId}")
    public ResponseEntity<UserMeasurement> getUserMeasurement(@PathVariable String userMeasurementId) {
        return userMeasurementService.getUserMeasurement(userMeasurementId);
    }

    @PostMapping
    public ResponseEntity<UserMeasurement> createUserMeasurement(@RequestBody UserMeasurement userMeasurement) {
        return userMeasurementService.createUserMeasurement(userMeasurement);
    }

    @PutMapping("/{userMeasurementId}")
    public ResponseEntity<UserMeasurement> updateUserMeasurement(@PathVariable String userMeasurementId, UserMeasurement userMeasurement) {
        return userMeasurementService.updateUserMeasurement(userMeasurementId, userMeasurement);
    }

    @DeleteMapping("/{userMeasurementId}")
    public ResponseEntity<UserMeasurement> deleteUserMeasurement(@PathVariable String userMeasurementId) {
        return userMeasurementService.deleteUserMeasurement(userMeasurementId);
    }
}
