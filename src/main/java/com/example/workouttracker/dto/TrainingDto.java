package com.example.workouttracker.dto;

import com.example.workouttracker.trainingCategory.TrainingCategoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrainingDto {
    private String id;
    private String name;
    private String description;
    private List<ExerciseDto> exercises;
    private List<TrainingCategoryEntity> trainingCategories;
}
