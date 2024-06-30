package com.example.workouttracker.core.userMeasurement;

import com.example.workouttracker.core.exception.UserMeasurementException;
import com.example.workouttracker.mapper.UserMeasurementMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.openapitools.model.UserMeasurement;
import org.openapitools.model.UserMeasurementCreate;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserMeasurementService {

    private final UserMeasurementRepository userMeasurementRepository;

    private final UserMeasurementMapper userMeasurementMapper;

    public List<UserMeasurement> getUserMeasurements(Integer page, Integer size) {
        return userMeasurementRepository.findAll(PageRequest.of(page, size))
                .map(userMeasurementMapper::toDto).getContent();
    }

    public UserMeasurement getUserMeasurement(String userMeasurementId) {
        return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .map(userMeasurementMapper::toDto)
                .orElseThrow(() -> new UserMeasurementException(UserMeasurementException.FailReason.NOT_FOUND));
    }

    public UserMeasurement createUserMeasurement(UserMeasurementCreate userMeasurementCreate) {
        UserMeasurementEntity newUserMeasurementEntity = userMeasurementMapper.toEntity(userMeasurementCreate);

        userMeasurementRepository.save(newUserMeasurementEntity);

        return userMeasurementMapper.toDto(newUserMeasurementEntity);
    }

    public UserMeasurement updateUserMeasurement(String userMeasurementId, UserMeasurementCreate userMeasurementCreate) {
        UserMeasurementEntity existingUserMeasurementEntity = userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .orElseThrow(() -> new UserMeasurementException(UserMeasurementException.FailReason.NOT_FOUND));

        existingUserMeasurementEntity.setWeight(userMeasurementCreate.getWeight().doubleValue());
        existingUserMeasurementEntity.setHeight(userMeasurementCreate.getHeight().doubleValue());
        existingUserMeasurementEntity.setAge(userMeasurementCreate.getAge().doubleValue());

        userMeasurementRepository.save(existingUserMeasurementEntity);

        return userMeasurementMapper.toDto(existingUserMeasurementEntity);
    }

    public void deleteUserMeasurement(String userMeasurementId) {
        UserMeasurementEntity userMeasurementEntity = userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                .orElseThrow(() -> new UserMeasurementException(UserMeasurementException.FailReason.NOT_FOUND));
        userMeasurementRepository.delete(userMeasurementEntity);
    }
}
