package com.example.workouttracker.core.training;

import com.example.workouttracker.core.exception.TrainingException;
import com.example.workouttracker.core.user.UserRepository;
import com.example.workouttracker.mapper.TrainingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Training;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.openapitools.model.TrainingDetails;
import org.openapitools.model.TrainingCreate;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final TrainingMapper trainingMapper;

    public List<Training> getTrainings(Integer page, Integer size) {
        log.info("Fetching trainings with page: {} and size: {}", page, size);
        return trainingRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdOn")))
                .map(trainingMapper::toDto)
                .getContent();
    }

    public TrainingDetails getTraining(String trainingId) {
        log.info("Fetching training with ID: {}", trainingId);
        try {
            return trainingRepository.findById(UUID.fromString(trainingId))
                    .map(trainingMapper::toDetailsDto)
                    .orElseThrow(() -> new TrainingException(TrainingException.FailReason.NOT_FOUND));
        } catch (TrainingException e) {
            log.error("Training not found: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while getting the training: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Training createTraining(TrainingCreate trainingCreate) {
        log.info("Creating new training with details: {}", trainingCreate);
        try {
            TrainingEntity newTrainingEntity = trainingMapper.toEntity(trainingCreate);
            newTrainingEntity.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new TrainingException(TrainingException.FailReason.USER_NOT_FOUND)));

            trainingRepository.save(newTrainingEntity);

            return trainingMapper.toDto(newTrainingEntity);
        } catch (TrainingException e) {
            log.error("User not found for training creation: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while creating the training: {}", e.getMessage(), e);
            throw e;
        }
    }

    public TrainingDetails updateTraining(String trainingId, TrainingCreate trainingCreate) {
        log.info("Updating training with ID: {}", trainingId);
        try {
            TrainingEntity trainingEntity = trainingRepository.findById(UUID.fromString(trainingId))
                    .orElseThrow(() -> new TrainingException(TrainingException.FailReason.NOT_FOUND));

            trainingEntity.setName(trainingCreate.getName());
            trainingEntity.setDescription(trainingCreate.getDescription());

            trainingRepository.save(trainingEntity);

            return trainingMapper.toDetailsDto(trainingEntity);
        } catch (TrainingException e) {
            log.error("Training not found for update: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating the training: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteTraining(String trainingId) {
        log.info("Deleting training with ID: {}", trainingId);
        try {
            TrainingEntity training = trainingRepository.findById(UUID.fromString(trainingId))
                    .orElseThrow(() -> new TrainingException(TrainingException.FailReason.NOT_FOUND));
            trainingRepository.delete(training);
            log.info("Training with ID: {} deleted successfully", trainingId);
        } catch (TrainingException e) {
            log.error("Training not found for deletion: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting the training: {}", e.getMessage(), e);
            throw e;
        }
    }
}
