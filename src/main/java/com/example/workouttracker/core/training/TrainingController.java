package com.example.workouttracker.core.training;

import com.example.api.TrainingApi;
import com.example.model.*;
import com.example.workouttracker.core.exception.TrainingException;
import com.example.workouttracker.core.utils.ExcelGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
public class TrainingController implements TrainingApi {

    private final TrainingService trainingService;

    @Override
    public ResponseEntity<List<Training>> getTrainingsPage(@RequestParam Integer page,
                                                       @RequestParam Integer size) {
        List<Training> trainings = trainingService.getTrainingsPage(page, size);
        return ResponseEntity.ok(trainings);
    }

    @Override
    public ResponseEntity<TrainingDetails> getTrainingById(@PathVariable String trainingId) {
        TrainingDetails training = trainingService.getTraining(trainingId);
        return ResponseEntity.ok(training);
    }

    @Override
    public ResponseEntity<Training> createTraining(@RequestBody @Valid TrainingCreate trainingCreate) {
        Training createdTraining = trainingService.createTraining(trainingCreate);
        return ResponseEntity.ok(createdTraining);
    }

    @Override
    public ResponseEntity<TrainingDetails> updateTraining(@PathVariable String trainingId, @RequestBody @Valid TrainingCreate trainingCreate) {
        TrainingDetails updatedTraining = trainingService.updateTraining(trainingId, trainingCreate);
        return ResponseEntity.ok(updatedTraining);
    }

    @Override
    public ResponseEntity<TrainingDetails> patchTrainingExercises(@PathVariable String trainingId, @RequestBody @Valid List<ExerciseUpdate> exercises) {
        TrainingDetails updatedTraining = trainingService.patchExercisesInTraining(trainingId, exercises);
        return ResponseEntity.ok(updatedTraining);
    }

    @Override
    public ResponseEntity<Void> deleteTraining(@PathVariable String trainingId) {
        trainingService.deleteTraining(trainingId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteExerciseFromTraining(@PathVariable String trainingId, @PathVariable String exerciseId) {
        trainingService.deleteExerciseFromTraining(trainingId, exerciseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
        List<TrainingDetails> trainings = trainingService.getTrainingsDetails();

        ByteArrayInputStream byteArrayInputStream = ExcelGenerator.trainingsToExcel(trainings);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=trainings.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(byteArrayInputStream.readAllBytes());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(TrainingException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        switch (e.getFailReason()) {
            case NOT_FOUND:
                errorResponse.setCode("TRAINING_NOT_FOUND");
                errorResponse.setMessage("Training not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            case USER_NOT_FOUND:
                errorResponse.setCode("USER_NOT_FOUND");
                errorResponse.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            default:
                errorResponse.setCode("INTERNAL_SERVER_ERROR");
                errorResponse.setMessage("Internal server error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
