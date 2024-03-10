package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.ExerciseDto;
import com.example.workouttracker.exercise.Exercise;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    Exercise toEntity(ExerciseDto exerciseDto);

    ExerciseDto toDto(Exercise exercise);

    List<ExerciseDto> toDto(List<Exercise> exercise);

    List<Exercise> toEntity(List<ExerciseDto> exerciseDto);
}
