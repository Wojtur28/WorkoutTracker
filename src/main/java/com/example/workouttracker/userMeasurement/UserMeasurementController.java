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
    public ResponseEntity<List<UserMeasurementEntity>> getUserMeasurements() {
        return userMeasurementService.getUserMeasurements();
    }

    @GetMapping("/{userMeasurementId}")
    public ResponseEntity<UserMeasurementEntity> getUserMeasurement(@PathVariable String userMeasurementId) {
        return userMeasurementService.getUserMeasurement(userMeasurementId);
    }

    @PostMapping
    public ResponseEntity<UserMeasurementEntity> createUserMeasurement(@RequestBody UserMeasurementEntity userMeasurementEntity) {
        return userMeasurementService.createUserMeasurement(userMeasurementEntity);
    }

    @PutMapping("/{userMeasurementId}")
    public ResponseEntity<UserMeasurementEntity> updateUserMeasurement(@PathVariable String userMeasurementId, UserMeasurementEntity userMeasurementEntity) {
        return userMeasurementService.updateUserMeasurement(userMeasurementId, userMeasurementEntity);
    }

    @DeleteMapping("/{userMeasurementId}")
    public ResponseEntity<UserMeasurementEntity> deleteUserMeasurement(@PathVariable String userMeasurementId) {
        return userMeasurementService.deleteUserMeasurement(userMeasurementId);
    }
}
