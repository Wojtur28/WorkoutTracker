package com.example.workouttracker.userMeasurement;

import com.example.workouttracker.mapper.UserMeasurementMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.openapitools.model.UserMeasurement;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserMeasurementService {

    private final UserMeasurementRepository userMeasurementRepository;

    private final UserMeasurementMapper userMeasurementMapper;

    public ResponseEntity<UserMeasurement> getUserMeasurement(String userMeasurementId) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(userMeasurementEntity -> ResponseEntity.ok(userMeasurementMapper.toDto(userMeasurementEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<UserMeasurement>> getUserMeasurements() {
        return ResponseEntity.ok(userMeasurementMapper.toDto(userMeasurementRepository.findAll()));
    }

    public ResponseEntity<UserMeasurementEntity> createUserMeasurement(UserMeasurementEntity userMeasurementEntity) {
        UserMeasurementEntity newUserMeasurementEntity = userMeasurementRepository.save(userMeasurementEntity);
        return ResponseEntity.ok(newUserMeasurementEntity);
    }

    public ResponseEntity<UserMeasurementEntity> updateUserMeasurement(String userMeasurementId, UserMeasurementEntity userMeasurementEntity) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(existingUserMeasurementEntity -> {
                    existingUserMeasurementEntity.setId(userMeasurementEntity.getId());
                    existingUserMeasurementEntity.setWeight(userMeasurementEntity.getWeight());
                    existingUserMeasurementEntity.setHeight(userMeasurementEntity.getHeight());
                    existingUserMeasurementEntity.setAge(userMeasurementEntity.getAge());
                    userMeasurementRepository.save(existingUserMeasurementEntity);
                    return ResponseEntity.ok(existingUserMeasurementEntity);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserMeasurementEntity> deleteUserMeasurement(String userMeasurementId) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(userMeasurementEntity -> {
                    userMeasurementRepository.delete(userMeasurementEntity);
                    return ResponseEntity.ok(userMeasurementEntity);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
