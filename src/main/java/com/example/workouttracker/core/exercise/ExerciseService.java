package com.example.workouttracker.core.exercise;

import com.example.workouttracker.mapper.ExerciseMapper;
import lombok.AllArgsConstructor;
import org.openapitools.model.Exercise;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    public ResponseEntity<List<Exercise>> getExercises() {
        return ResponseEntity.ok(exerciseMapper.toDto(exerciseRepository.findAll()));
    }

    public ResponseEntity<Exercise> getExercise(@PathVariable String exerciseId) {
        return exerciseRepository.findById(UUID.fromString(exerciseId))
                .map(exerciseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Exercise> createExercise(Exercise exercise) {
        ExerciseEntity newExerciseEntity = exerciseMapper.toEntity(exercise);
        exerciseRepository.save(newExerciseEntity);
        return ResponseEntity.ok(exerciseMapper.toDto(newExerciseEntity));
    }

    public ResponseEntity<Exercise> updateExercise(@PathVariable String exerciseId, Exercise exercise) {
        ExerciseEntity exerciseEntity = exerciseRepository.findById(UUID.fromString(exerciseId))
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        exerciseEntity.setName(exercise.getName());
        exerciseEntity.setDescription(exercise.getDescription());

        return ResponseEntity.ok(exerciseMapper.toDto(exerciseRepository.save(exerciseEntity)));
    }

    public ResponseEntity<Void> deleteExercise(@PathVariable String exerciseId) {
        return exerciseRepository.findById(UUID.fromString(exerciseId))
                .map(exercise -> {
                    exerciseRepository.delete(exercise);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}