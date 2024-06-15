package com.example.workouttracker.core.training;

import com.example.workouttracker.core.user.UserRepository;
import com.example.workouttracker.mapper.TrainingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Training;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class TrainingService {

    private final TrainingRepository trainingRepository;

    private final UserRepository userRepository;

    private final TrainingMapper trainingMapper;

    public ResponseEntity<List<Training>> getTrainings(Integer page, Integer size) {
        return ResponseEntity.ok(trainingRepository.findAll(PageRequest.of(page, size)).map(trainingMapper::toDto).getContent());
    }

    public ResponseEntity<Training> getTraining(String trainingId) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(trainingMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Training> createTraining(Training training) {
        TrainingEntity newTrainingEntity = trainingMapper.toEntity(training);
        newTrainingEntity.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()
                -> new RuntimeException("User not found")));
        trainingRepository.save(newTrainingEntity);
        return ResponseEntity.ok(trainingMapper.toDto(newTrainingEntity));
    }

    public ResponseEntity<Training> updateTraining(String trainingId, Training training) {
        return trainingRepository.findById(UUID.fromString(trainingId))
                .map(trainingEntity -> {
                    trainingEntity.setName(training.getName());
                    trainingEntity.setDescription(training.getDescription());
                    return ResponseEntity.ok(trainingMapper.toDto(trainingRepository.save(trainingEntity)));
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
