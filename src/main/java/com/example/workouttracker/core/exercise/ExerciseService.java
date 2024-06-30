package com.example.workouttracker.core.exercise;

import com.example.workouttracker.core.exception.ExerciseException;
import com.example.workouttracker.mapper.ExerciseMapper;
import lombok.AllArgsConstructor;
import org.openapitools.model.Exercise;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.openapitools.model.ExerciseCreate;

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

    public Exercise createExercise(ExerciseCreate exerciseCreate) {
        ExerciseEntity newExerciseEntity = exerciseMapper.toEntity(exerciseCreate);
        exerciseRepository.save(newExerciseEntity);
        return exerciseMapper.toDto(newExerciseEntity);
    }

    public Exercise updateExercise(String exerciseId, ExerciseCreate exerciseCreate) {
        ExerciseEntity existingExercise = exerciseRepository.findById(UUID.fromString(exerciseId))
                .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.NOT_FOUND));

        existingExercise.setName(exerciseCreate.getName());
        existingExercise.setDescription(exerciseCreate.getDescription());
        existingExercise.setSets(exerciseCreate.getSets());
        existingExercise.setReps(exerciseCreate.getReps());

        exerciseRepository.save(existingExercise);
        return exerciseMapper.toDto(existingExercise);
    }

    public void deleteExercise(String exerciseId) {
        ExerciseEntity exercise = exerciseRepository.findById(UUID.fromString(exerciseId))
                .orElseThrow(() -> new ExerciseException(ExerciseException.FailReason.NOT_FOUND));
        exerciseRepository.delete(exercise);
    }
}
