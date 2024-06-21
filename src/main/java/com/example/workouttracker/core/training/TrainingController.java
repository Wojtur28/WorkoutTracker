package com.example.workouttracker.core.training;

import com.example.workouttracker.core.exception.TrainingException;
import lombok.AllArgsConstructor;
import org.openapitools.model.Training;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.openapitools.api.TrainingApi;
import org.openapitools.model.ErrorResponse;
import org.openapitools.model.TrainingDetails;

import java.util.List;

@RestController
@AllArgsConstructor
public class TrainingController implements TrainingApi {

    private final TrainingService trainingService;

    @Override
    public ResponseEntity<List<Training>> getTrainings(@RequestParam Integer page,
                                                       @RequestParam Integer size) {
        List<Training> trainings = trainingService.getTrainings(page, size);
        return ResponseEntity.ok(trainings);
    }

    @Override
    public ResponseEntity<TrainingDetails> getTrainingById(@PathVariable String trainingId) {
        TrainingDetails training = trainingService.getTraining(trainingId);
        return ResponseEntity.ok(training);
    }

    @Override
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        Training createdTraining = trainingService.createTraining(training);
        return ResponseEntity.ok(createdTraining);
    }

    @Override
    public ResponseEntity<TrainingDetails> updateTraining(@PathVariable String trainingId, @RequestBody TrainingDetails trainingDetails) {
        TrainingDetails updatedTraining = trainingService.updateTraining(trainingId, trainingDetails);
        return ResponseEntity.ok(updatedTraining);
    }

    @Override
    public ResponseEntity<Void> deleteTraining(@PathVariable String trainingId) {
        trainingService.deleteTraining(trainingId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(TrainingException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        switch (e.getFailReason()) {
            case NOT_FOUND:
                errorResponse.setCode("TRAINING_NOT_FOUND");
                errorResponse.setMessage("Training not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.toString());
            case USER_NOT_FOUND:
                errorResponse.setCode("USER_NOT_FOUND");
                errorResponse.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.toString());
            default:
                errorResponse.setCode("INTERNAL_SERVER_ERROR");
                errorResponse.setMessage("Internal server error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
        }
    }
}
