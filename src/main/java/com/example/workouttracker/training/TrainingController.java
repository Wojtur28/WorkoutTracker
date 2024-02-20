package com.example.workouttracker.training;

import com.example.workouttracker.dto.TrainingDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getTrainings() {
        return trainingService.getTrainings();
    }

    @GetMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> getTraining(@PathVariable String trainingId) {
        return trainingService.getTraining(trainingId);
    }

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        return trainingService.createTraining(training);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<Training> updateTraining(@PathVariable String trainingId, @RequestBody Training training) {
        return trainingService.updateTraining(trainingId, training);
    }

    @DeleteMapping("/{trainingId}")
    public ResponseEntity<Training> deleteTraining(@PathVariable String trainingId) {
        return trainingService.deleteTraining(trainingId);
    }
}
