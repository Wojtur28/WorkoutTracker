package com.example.workouttracker.core.userMeasurement;

import com.example.workouttracker.core.exception.UserMeasurementException;
import com.example.workouttracker.mapper.UserMeasurementMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.openapitools.model.UserMeasurement;
import org.openapitools.model.UserMeasurementCreate;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserMeasurementService {

    private final UserMeasurementRepository userMeasurementRepository;
    private final UserMeasurementMapper userMeasurementMapper;

    public List<UserMeasurement> getUserMeasurements(Integer page, Integer size) {
        log.info("Fetching user measurements with page: {} and size: {}", page, size);
        return userMeasurementRepository.findAll(PageRequest.of(page, size))
                .map(userMeasurementMapper::toDto).getContent();
    }

    public UserMeasurement getUserMeasurement(String userMeasurementId) {
        log.info("Fetching user measurement with ID: {}", userMeasurementId);
        try {
            return userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                    .map(userMeasurementMapper::toDto)
                    .orElseThrow(() -> new UserMeasurementException(UserMeasurementException.FailReason.NOT_FOUND));
        } catch (UserMeasurementException e) {
            log.error("User measurement not found: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while getting the user measurement: {}", e.getMessage(), e);
            throw e;
        }
    }

    public UserMeasurement createUserMeasurement(UserMeasurementCreate userMeasurementCreate) {
        log.info("Creating new user measurement with details: {}", userMeasurementCreate);
        try {
            UserMeasurementEntity newUserMeasurementEntity = userMeasurementMapper.toEntity(userMeasurementCreate);
            userMeasurementRepository.save(newUserMeasurementEntity);
            return userMeasurementMapper.toDto(newUserMeasurementEntity);
        } catch (Exception e) {
            log.error("An error occurred while creating the user measurement: {}", e.getMessage(), e);
            throw e;
        }
    }

    public UserMeasurement updateUserMeasurement(String userMeasurementId, UserMeasurementCreate userMeasurementCreate) {
        log.info("Updating user measurement with ID: {}", userMeasurementId);
        try {
            UserMeasurementEntity existingUserMeasurementEntity = userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                    .orElseThrow(() -> new UserMeasurementException(UserMeasurementException.FailReason.NOT_FOUND));

            existingUserMeasurementEntity.setWeight(userMeasurementCreate.getWeight().doubleValue());
            existingUserMeasurementEntity.setHeight(userMeasurementCreate.getHeight().doubleValue());
            existingUserMeasurementEntity.setAge(userMeasurementCreate.getAge().doubleValue());

            userMeasurementRepository.save(existingUserMeasurementEntity);
            return userMeasurementMapper.toDto(existingUserMeasurementEntity);
        } catch (UserMeasurementException e) {
            log.error("User measurement not found for update: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating the user measurement: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteUserMeasurement(String userMeasurementId) {
        log.info("Deleting user measurement with ID: {}", userMeasurementId);
        try {
            UserMeasurementEntity userMeasurementEntity = userMeasurementRepository.findById(UUID.fromString(userMeasurementId))
                    .orElseThrow(() -> new UserMeasurementException(UserMeasurementException.FailReason.NOT_FOUND));
            userMeasurementRepository.delete(userMeasurementEntity);
            log.info("User measurement with ID: {} deleted successfully", userMeasurementId);
        } catch (UserMeasurementException e) {
            log.error("User measurement not found for deletion: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting the user measurement: {}", e.getMessage(), e);
            throw e;
        }
    }
}
