package com.example.workouttracker.core.exercise;

import com.example.api.ExerciseApi;
import com.example.model.Exercise;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ExerciseController implements ExerciseApi {

    private final ExerciseService exerciseService;

    @Override
    public ResponseEntity<List<Exercise>> getExercisesByName(@PathVariable String exerciseName) {
        List<Exercise> exercises = exerciseService.getExercisesByName(exerciseName);

        return ResponseEntity.ok(exercises);
    }
}
