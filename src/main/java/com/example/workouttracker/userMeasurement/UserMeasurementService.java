package com.example.workouttracker.userMeasurement;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserMeasurementService {

    private final UserMeasurementRepository userMeasurementRepository;

    public ResponseEntity<UserMeasurement> getUserMeasurement(String userMeasurementId) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<UserMeasurement>> getUserMeasurements() {
        return ResponseEntity.ok(userMeasurementRepository.findAll());
    }

    public ResponseEntity<UserMeasurement> createUserMeasurement(UserMeasurement userMeasurement) {
        UserMeasurement newUserMeasurement = userMeasurementRepository.save(userMeasurement);
        return ResponseEntity.ok(newUserMeasurement);
    }

    public ResponseEntity<UserMeasurement> updateUserMeasurement(String userMeasurementId, UserMeasurement userMeasurement) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(existingUserMeasurement -> {
                    existingUserMeasurement.setId(userMeasurement.getId());
                    existingUserMeasurement.setWeight(userMeasurement.getWeight());
                    existingUserMeasurement.setHeight(userMeasurement.getHeight());
                    existingUserMeasurement.setAge(userMeasurement.getAge());
                    userMeasurementRepository.save(existingUserMeasurement);
                    return ResponseEntity.ok(existingUserMeasurement);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserMeasurement> deleteUserMeasurement(String userMeasurementId) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(userMeasurement -> {
                    userMeasurementRepository.delete(userMeasurement);
                    return ResponseEntity.ok(userMeasurement);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
