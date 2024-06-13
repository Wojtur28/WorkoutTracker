package com.example.workouttracker.core.training;

import lombok.AllArgsConstructor;
import org.openapitools.model.Training;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.openapitools.api.TrainingApi;

import java.util.List;

@RestController
@AllArgsConstructor
public class TrainingController implements TrainingApi {

    private final TrainingService trainingService;

    @Override
    public ResponseEntity<List<Training>> getTrainings(@RequestParam Integer page,
                                                       @RequestParam Integer size) {
        return trainingService.getTrainings(page, size);
    }

    @Override
    public ResponseEntity<Training> getTrainingById(@PathVariable String trainingId) {
        return trainingService.getTraining(trainingId);
    }

    @Override
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        return trainingService.createTraining(training);
    }

    @Override
    public ResponseEntity<Training> updateTraining(@PathVariable String trainingId, @RequestBody Training training) {
        return trainingService.updateTraining(trainingId, training);
    }

    @Override
    public ResponseEntity<Void> deleteTraining(@PathVariable String trainingId) {
        return trainingService.deleteTraining(trainingId);
    }
}
