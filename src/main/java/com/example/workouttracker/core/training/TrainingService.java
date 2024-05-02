package com.example.workouttracker.core.training;

import com.example.workouttracker.mapper.TrainingMapper;
import lombok.AllArgsConstructor;
import org.openapitools.model.Training;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    public ResponseEntity<List<Training>> getTrainings() {
        return ResponseEntity.ok(trainingMapper.toDto(trainingRepository.findAll()));
    }

    public ResponseEntity<Training> getTraining(String trainingId) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(trainingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Training> createTraining(Training training) {
        TrainingEntity newTrainingEntity = trainingMapper.toEntity(training);
        trainingRepository.save(newTrainingEntity);
        return ResponseEntity.ok(trainingMapper.toDto(newTrainingEntity));
    }

    public ResponseEntity<Training> updateTraining(String trainingId, Training training) {
        TrainingEntity trainingEntity = trainingRepository.findById(UUID.fromString(trainingId))
                .orElseThrow(() -> new RuntimeException("Training not found"));

        trainingEntity.setName(training.getName());
        trainingEntity.setDescription(training.getDescription());

        return ResponseEntity.ok(trainingMapper.toDto(trainingRepository.save(trainingEntity)));
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
