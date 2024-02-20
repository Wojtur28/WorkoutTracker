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

    public ResponseEntity<Training> createTraining(Training training) {
        Training newTraining = trainingRepository.save(training);
        return ResponseEntity.ok(newTraining);
    }

    public ResponseEntity<Training> updateTraining(String trainingId, Training training) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(existingTraining -> {
                    existingTraining.setName(training.getName());
                    existingTraining.setDescription(training.getDescription());
                    existingTraining.setExercises(training.getExercises());
                    existingTraining.setTrainingCategories(training.getTrainingCategories());
                    trainingRepository.save(existingTraining);
                    return ResponseEntity.ok(existingTraining);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Training> deleteTraining(String trainingId) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(training -> {
                    trainingRepository.delete(training);
                    return ResponseEntity.ok(training);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
