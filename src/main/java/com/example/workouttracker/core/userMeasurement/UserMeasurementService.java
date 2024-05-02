package com.example.workouttracker.core.userMeasurement;

import com.example.workouttracker.core.user.UserEntity;
import com.example.workouttracker.core.user.UserRepository;
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

    private final UserRepository userRepository;

    public ResponseEntity<UserMeasurement> getUserMeasurement(String userMeasurementId) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(userMeasurementEntity -> ResponseEntity.ok(userMeasurementMapper.toDto(userMeasurementEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<UserMeasurement>> getUserMeasurements() {
        return ResponseEntity.ok(userMeasurementMapper.toDto(userMeasurementRepository.findAll()));
    }

    public ResponseEntity<UserMeasurement> createUserMeasurement(UserMeasurement userMeasurement) {
         UserEntity user = userRepository.findById(UUID.fromString(userMeasurement.getUser().getId())).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserMeasurementEntity newUserMeasurementEntity = userMeasurementMapper.toEntity(userMeasurement);
        newUserMeasurementEntity.setUser(user);
        userMeasurementRepository.save(newUserMeasurementEntity);
        return ResponseEntity.ok(userMeasurementMapper.toDto(newUserMeasurementEntity));
    }

    public ResponseEntity<UserMeasurement> updateUserMeasurement(String userMeasurementId, UserMeasurement userMeasurement) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(existingUserMeasurementEntity -> {
                    UserMeasurementEntity updatedUserMeasurementEntity = userMeasurementMapper.toEntity(userMeasurement);
                    updatedUserMeasurementEntity.setId(existingUserMeasurementEntity.getId());
                    userMeasurementRepository.save(updatedUserMeasurementEntity);
                    return ResponseEntity.ok(userMeasurementMapper.toDto(updatedUserMeasurementEntity));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteUserMeasurement(String userMeasurementId) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(userMeasurementEntity -> {
                    userMeasurementRepository.delete(userMeasurementEntity);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
