package com.example.workouttracker.core.exercise;

import com.example.workouttracker.core.exception.ExerciseException;
import com.example.workouttracker.mapper.ExerciseMapper;
import lombok.AllArgsConstructor;
import org.openapitools.model.Exercise;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    public List<Exercise> getExercises(Integer size, Integer page) {
        return exerciseRepository.findAll(PageRequest.of(page, size))
                .map(exerciseMapper::toDto).getContent();
    }

    public Exercise getExercise(String exerciseId) {
        return exerciseRepository.findById(UUID.fromString(exerciseId))
                .map(exerciseMapper::toDto)
                .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.NOT_FOUND));
    }

    public Exercise createExercise(Exercise exercise) {
        ExerciseEntity newExerciseEntity = exerciseMapper.toEntity(exercise);
        exerciseRepository.save(newExerciseEntity);
        return exerciseMapper.toDto(newExerciseEntity);
    }

    public Exercise updateExercise(String exerciseId, Exercise exercise) {
        ExerciseEntity existingExercise = exerciseRepository.findById(UUID.fromString(exerciseId))
                .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.NOT_FOUND));

        existingExercise.setName(exercise.getName());
        existingExercise.setDescription(exercise.getDescription());
        existingExercise.setSets(exercise.getSets());
        existingExercise.setReps(exercise.getReps());

        exerciseRepository.save(existingExercise);
        return exerciseMapper.toDto(existingExercise);
    }

    public void deleteExercise(String exerciseId) {
        ExerciseEntity exercise = exerciseRepository.findById(UUID.fromString(exerciseId))
                .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.NOT_FOUND));
        exerciseRepository.delete(exercise);
    }
}
