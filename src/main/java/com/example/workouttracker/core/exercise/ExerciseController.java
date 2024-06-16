package com.example.workouttracker.core.exercise;

import com.example.workouttracker.core.exception.ExerciseException;
import lombok.AllArgsConstructor;
import org.openapitools.model.Exercise;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.openapitools.api.ExerciseApi;
import org.openapitools.model.ErrorResponse;

import java.util.List;

@RestController
@AllArgsConstructor
public class ExerciseController implements ExerciseApi {

    private final ExerciseService exerciseService;

    @Override
    public ResponseEntity<List<Exercise>> getExercises(@RequestParam Integer page,
                                                       @RequestParam Integer size) {
        List<Exercise> exercises = exerciseService.getExercises(size, page);
        return ResponseEntity.ok(exercises);
    }

    @Override
    public ResponseEntity<Exercise> getExerciseById(@PathVariable String exerciseId) {
        Exercise exercise = exerciseService.getExercise(exerciseId);
        return ResponseEntity.ok(exercise);
    }

    @Override
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.createExercise(exercise);
        return ResponseEntity.ok(createdExercise);
    }

    @Override
    public ResponseEntity<Exercise> updateExercise(@PathVariable String exerciseId, @RequestBody Exercise exercise) {
        Exercise updatedExercise = exerciseService.updateExercise(exerciseId, exercise);
        return ResponseEntity.ok(updatedExercise);
    }

    @Override
    public ResponseEntity<Void> deleteExercise(@PathVariable String exerciseId) {
        exerciseService.deleteExercise(exerciseId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ExerciseException.class)
    public ResponseEntity<String> handleException(ExerciseException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        switch (e.getFailReason()) {
            case NOT_FOUND:
                errorResponse.setCode("EXERCISE_NOT_FOUND");
                errorResponse.setMessage("Exercise not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.toString());
            case TRAINING_NOT_FOUND:
                errorResponse.setCode("TRAINING_NOT_FOUND");
                errorResponse.setMessage("Training not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.toString());
            default:
                errorResponse.setCode("INTERNAL_SERVER_ERROR");
                errorResponse.setMessage("Internal server error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }
}
