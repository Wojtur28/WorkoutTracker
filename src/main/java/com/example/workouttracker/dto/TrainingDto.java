package com.example.workouttracker.dto;

import com.example.workouttracker.training.TrainingCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainingDto {
    private String id;
    private String name;
    private String description;
    private List<ExerciseDto> exercisesDto;
    private List<TrainingCategory> trainingCategories;
}
