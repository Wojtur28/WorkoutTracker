package com.example.workouttracker.exercise;

import com.example.workouttracker.dto.ExerciseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getExercisesPage() {
        return exerciseService.getExercises();
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> getExercise(@PathVariable String exerciseId) {
        return exerciseService.getExercise(exerciseId);
    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        return exerciseService.createExercise(exercise);
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable String exerciseId, Exercise exercise) {
        return exerciseService.updateExercise(exerciseId, exercise);
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Exercise> deleteExercise(@PathVariable String exerciseId) {
        return exerciseService.deleteExercise(exerciseId);
    }
}
