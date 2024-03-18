package com.example.workouttracker.exercise;

import com.example.workouttracker.dto.ExerciseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ExerciseEntity> createExercise(@RequestBody ExerciseEntity exerciseEntity) {
        return exerciseService.createExercise(exerciseEntity);
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseEntity> updateExercise(@PathVariable String exerciseId, @RequestBody ExerciseEntity exerciseEntity) {
        return exerciseService.updateExercise(exerciseId, exerciseEntity);
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable String exerciseId) {
        return exerciseService.deleteExercise(exerciseId);
    }
}
