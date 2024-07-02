package com.example.workouttracker.core.exercise;

import com.example.workouttracker.core.exception.ExerciseException;
import com.example.workouttracker.core.training.TrainingEntity;
import com.example.workouttracker.core.training.TrainingRepository;
import com.example.workouttracker.mapper.ExerciseMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Exercise;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.openapitools.model.ExerciseCreate;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseMapper exerciseMapper;

    public List<Exercise> getExercises(Integer size, Integer page) {
        log.info("Fetching exercises with page: {} and size: {}", page, size);
        return exerciseRepository.findAll(PageRequest.of(page, size))
                .map(exerciseMapper::toDto).getContent();
    }

    public Exercise getExercise(String exerciseId) {
        log.info("Fetching exercise with ID: {}", exerciseId);
        try {
            return exerciseRepository.findById(UUID.fromString(exerciseId))
                    .map(exerciseMapper::toDto)
                    .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.NOT_FOUND));
        } catch (ExerciseException e) {
            log.error("Exercise not found: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while getting the exercise: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Exercise createExercise(ExerciseCreate exerciseCreate) {
        log.info("Creating new exercise with details: {}", exerciseCreate);
        try {
            Optional<TrainingEntity> training = Optional.ofNullable(trainingRepository.findById(UUID.fromString(exerciseCreate.getTrainingId()))
                    .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.TRAINING_NOT_FOUND)));

            ExerciseEntity newExerciseEntity = exerciseMapper.toEntity(exerciseCreate);
            newExerciseEntity.setTraining(training.get());
            exerciseRepository.save(newExerciseEntity);
            return exerciseMapper.toDto(newExerciseEntity);
        } catch (Exception e) {
            log.error("An error occurred while creating the exercise: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Exercise updateExercise(String exerciseId, ExerciseCreate exerciseCreate) {
        log.info("Updating exercise with ID: {}", exerciseId);
        try {
            ExerciseEntity existingExercise = exerciseRepository.findById(UUID.fromString(exerciseId))
                    .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.NOT_FOUND));

            existingExercise.setName(exerciseCreate.getName());
            existingExercise.setDescription(exerciseCreate.getDescription());
            existingExercise.setSets(exerciseCreate.getSets());
            existingExercise.setReps(exerciseCreate.getReps());

            exerciseRepository.save(existingExercise);
            return exerciseMapper.toDto(existingExercise);
        } catch (ExerciseException e) {
            log.error("Exercise not found for update: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while updating the exercise: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteExercise(String exerciseId) {
        log.info("Deleting exercise with ID: {}", exerciseId);
        try {
            ExerciseEntity exercise = exerciseRepository.findById(UUID.fromString(exerciseId))
                    .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.NOT_FOUND));
            exerciseRepository.delete(exercise);
        } catch (ExerciseException e) {
            log.error("Exercise not found for deletion: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while deleting the exercise: {}", e.getMessage(), e);
            throw e;
        }
    }
}
