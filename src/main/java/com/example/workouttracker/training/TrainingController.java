package com.example.workouttracker.training;

import lombok.AllArgsConstructor;
import org.openapitools.model.Training;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<Training>> getTrainings() {
        return trainingService.getTrainings();
    }

    @GetMapping("/{trainingId}")
    public ResponseEntity<Training> getTraining(@PathVariable String trainingId) {
        return trainingService.getTraining(trainingId);
    }

    @PostMapping
    public ResponseEntity<TrainingEntity> createTraining(@RequestBody TrainingEntity trainingEntity) {
        return trainingService.createTraining(trainingEntity);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingEntity> updateTraining(@PathVariable String trainingId, @RequestBody TrainingEntity trainingEntity) {
        return trainingService.updateTraining(trainingId, trainingEntity);
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Void> deleteTraining(@PathVariable String trainingId) {
        return trainingService.deleteTraining(trainingId);
    }
}
