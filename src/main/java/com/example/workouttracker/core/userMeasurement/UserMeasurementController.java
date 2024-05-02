package com.example.workouttracker.core.userMeasurement;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.openapitools.model.UserMeasurement;
import org.openapitools.api.UserMeasurementApi;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserMeasurementController implements UserMeasurementApi {

    private final UserMeasurementService userMeasurementService;

    @Override
    public ResponseEntity<List<UserMeasurement>> getUserMeasurements() {
        return userMeasurementService.getUserMeasurements();
    }

    @Override
    public ResponseEntity<UserMeasurement> getUserMeasurementById(@PathVariable String userMeasurementId) {
        return userMeasurementService.getUserMeasurement(userMeasurementId);
    }

    @Override
    public ResponseEntity<UserMeasurement> createUserMeasurement(@RequestBody UserMeasurement userMeasurement) {
        return userMeasurementService.createUserMeasurement(userMeasurement);
    }

    @Override
    public ResponseEntity<UserMeasurement> updateUserMeasurement(@PathVariable String userMeasurementId, @RequestBody UserMeasurement userMeasurement) {
        return userMeasurementService.updateUserMeasurement(userMeasurementId, userMeasurement);
    }

    @Override
    public ResponseEntity<Void> deleteUserMeasurement(@PathVariable String userMeasurementId) {
        return userMeasurementService.deleteUserMeasurement(userMeasurementId);
    }
}
