package com.example.workouttracker.dto;

import com.example.workouttracker.training.Training;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDto {
    private String id;
    private String name;
    private String description;
}
