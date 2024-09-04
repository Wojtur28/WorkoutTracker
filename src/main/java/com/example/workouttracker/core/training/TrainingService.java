package com.example.workouttracker.core.training;

import com.example.model.ExerciseUpdate;
import com.example.model.Training;
import com.example.model.TrainingCreate;
import com.example.model.TrainingDetails;
import com.example.workouttracker.core.exception.TrainingException;
import com.example.workouttracker.core.exercise.ExerciseEntity;
import com.example.workouttracker.core.exercise.ExerciseRepository;
import com.example.workouttracker.core.exercise.set.ExerciseSetEntity;
import com.example.workouttracker.core.user.UserRepository;
import com.example.workouttracker.mapper.TrainingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final TrainingMapper trainingMapper;
    private final ExerciseRepository exerciseRepository;

    public List<Training> getTrainings(Integer page, Integer size) {
        log.info("Fetching trainings with page: {} and size: {}", page, size);
        return trainingRepository.findByCreatedBy_Email(SecurityContextHolder.getContext().getAuthentication().getName(),PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdOn")))
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

    public TrainingDetails patchExercisesInTraining(String trainingId, List<ExerciseUpdate> exerciseUpdates) {
        log.info("Adding or updating exercises in training with ID: {}", trainingId);
        try {
            TrainingEntity trainingEntity = trainingRepository.findById(UUID.fromString(trainingId))
                    .orElseThrow(() -> new TrainingException(TrainingException.FailReason.NOT_FOUND));

            for (ExerciseUpdate update : exerciseUpdates) {
                ExerciseEntity exerciseEntity;

                if (update.getId() != null) {
                    exerciseEntity = trainingEntity.getExercises().stream()
                            .filter(exercise -> exercise.getId().equals(UUID.fromString(update.getId())))
                            .findFirst()
                            .orElseThrow(() -> new TrainingException(TrainingException.FailReason.EXERCISE_NOT_FOUND));

                    exerciseEntity.setName(update.getName());

                    exerciseEntity.getSets().clear();
                } else {
                    exerciseEntity = new ExerciseEntity();
                    exerciseEntity.setName(update.getName());
                    exerciseEntity.setTraining(trainingEntity);
                    trainingEntity.getExercises().add(exerciseEntity);
                }

                List<ExerciseSetEntity> updatedSets = update.getSets().stream()
                        .map(set -> {
                            ExerciseSetEntity setEntity = new ExerciseSetEntity();
                            setEntity.setReps(set.getReps());
                            setEntity.setWeight(set.getWeight());
                            setEntity.setExercise(exerciseEntity);
                            return setEntity;
                        }).toList();

                exerciseEntity.getSets().addAll(updatedSets);

                exerciseRepository.save(exerciseEntity);
            }

            trainingRepository.save(trainingEntity);
            return trainingMapper.toDetailsDto(trainingEntity);
        } catch (TrainingException e) {
            log.error("Error occurred while adding or updating exercises: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while adding or updating exercises: {}", e.getMessage(), e);
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

    public void deleteExerciseFromTraining(String trainingId, String exerciseId) {
        log.info("Deleting exercise with ID: {} from training with ID: {}", exerciseId, trainingId);
        try {
            TrainingEntity trainingEntity = trainingRepository.findById(UUID.fromString(trainingId))
                    .orElseThrow(() -> new TrainingException(TrainingException.FailReason.NOT_FOUND));

            ExerciseEntity exerciseEntity = trainingEntity.getExercises().stream()
                    .filter(exercise -> exercise.getId().equals(UUID.fromString(exerciseId)))
                    .findFirst()
                    .orElseThrow(() -> new TrainingException(TrainingException.FailReason.EXERCISE_NOT_FOUND));

            trainingEntity.getExercises().remove(exerciseEntity);
            exerciseRepository.delete(exerciseEntity);

            trainingRepository.save(trainingEntity);
            log.info("Exercise with ID: {} deleted successfully", exerciseId);
        } catch (TrainingException e) {
            log.error("Error occurred while deleting exercise: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting exercise: {}", e.getMessage(), e);
            throw e;
        }
    }

}
