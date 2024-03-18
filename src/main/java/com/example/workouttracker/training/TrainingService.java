package com.example.workouttracker.training;

import com.example.workouttracker.dto.TrainingDto;
import com.example.workouttracker.mapper.TrainingMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    public ResponseEntity<List<TrainingDto>> getTrainings() {
        return ResponseEntity.ok(trainingMapper.toDto(trainingRepository.findAll()));
    }

    public ResponseEntity<TrainingDto> getTraining(String trainingId) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(trainingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<TrainingEntity> createTraining(TrainingEntity trainingEntity) {
        TrainingEntity newTrainingEntity = trainingRepository.save(trainingEntity);
        return ResponseEntity.ok(newTrainingEntity);
    }

    public ResponseEntity<TrainingEntity> updateTraining(String trainingId, TrainingEntity trainingEntity) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(existingTraining -> {
                    existingTraining.setName(trainingEntity.getName());
                    existingTraining.setDescription(trainingEntity.getDescription());
                    existingTraining.setExerciseEntities(trainingEntity.getExerciseEntities());
                    existingTraining.setTrainingCategories(trainingEntity.getTrainingCategories());
                    trainingRepository.save(existingTraining);
                    return ResponseEntity.ok(existingTraining);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deleteTraining(String trainingId) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(training -> {
                    trainingRepository.delete(training);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
