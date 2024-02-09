package com.example.workouttracker.exercise;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ResponseEntity<List<Exercise>> getExercises() {
        return ResponseEntity.ok(exerciseRepository.findAll());
    }

    public ResponseEntity<Exercise> getExercise(@PathVariable String exerciseId) {
        return exerciseRepository.findById(UUID.fromString(exerciseId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Exercise> createExercise(Exercise exercise) {
        Exercise newExercise = exerciseRepository.save(exercise);
        return ResponseEntity.ok(newExercise);
    }

    public ResponseEntity<Exercise> updateExercise(@PathVariable String exerciseId, Exercise exercise) {
        return exerciseRepository.findById(UUID.fromString(exerciseId))
                .map(existingExercise -> {
                    existingExercise.setName(exercise.getName());
                    existingExercise.setDescription(exercise.getDescription());
                    exerciseRepository.save(existingExercise);
                    return ResponseEntity.ok(existingExercise);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Exercise> deleteExercise(@PathVariable String exerciseId) {
        return exerciseRepository.findById(UUID.fromString(exerciseId))
                .map(exercise -> {
                    exerciseRepository.delete(exercise);
                    return ResponseEntity.ok(exercise);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
