package com.example.workouttracker.exercise;

import com.example.workouttracker.dto.ExerciseDto;
import com.example.workouttracker.mapper.ExerciseMapper;
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

    private final ExerciseMapper exerciseMapper;

    public ResponseEntity<List<ExerciseDto>> getExercises() {
        return ResponseEntity.ok(exerciseMapper.toDto(exerciseRepository.findAll()));
    }

    public ResponseEntity<ExerciseDto> getExercise(@PathVariable String exerciseId) {
        return exerciseRepository.findById(UUID.fromString(exerciseId))
                .map(exerciseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<ExerciseEntity> createExercise(ExerciseEntity exerciseEntity) {
        ExerciseEntity newExerciseEntity = exerciseRepository.save(exerciseEntity);
        return ResponseEntity.ok(newExerciseEntity);
    }

    public ResponseEntity<ExerciseEntity> updateExercise(@PathVariable String exerciseId, ExerciseEntity exerciseEntity) {
        return exerciseRepository.findById(UUID.fromString(exerciseId))
                .map(existingExercise -> {
                    existingExercise.setName(exerciseEntity.getName());
                    existingExercise.setDescription(exerciseEntity.getDescription());
                    existingExercise.setTraining(exerciseEntity.getTraining());
                    exerciseRepository.save(existingExercise);
                    return ResponseEntity.ok(existingExercise);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
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
