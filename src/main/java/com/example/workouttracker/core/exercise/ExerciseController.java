package com.example.workouttracker.core.exercise;

import lombok.AllArgsConstructor;
import org.openapitools.model.Exercise;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.openapitools.api.ExerciseApi;

import java.util.List;

@RestController
@AllArgsConstructor
public class ExerciseController implements ExerciseApi {

    private final ExerciseService exerciseService;

    @Override
    public ResponseEntity<List<Exercise>> getExercises() {
        return exerciseService.getExercises();
    }

    @Override
    public ResponseEntity<Exercise> getExerciseById(@PathVariable String exerciseId) {
        return exerciseService.getExercise(exerciseId);
    }

    @Override
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        return exerciseService.createExercise(exercise);
    }

    @Override
    public ResponseEntity<Exercise> updateExercise(@PathVariable String exerciseId, @RequestBody Exercise exercise) {
        return exerciseService.updateExercise(exerciseId, exercise);
    }

    @Override
    public ResponseEntity<Void> deleteExercise(@PathVariable String exerciseId) {
        return exerciseService.deleteExercise(exerciseId);
    }
}
