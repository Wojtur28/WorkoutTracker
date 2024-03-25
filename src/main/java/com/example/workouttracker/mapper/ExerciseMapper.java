package com.example.workouttracker.mapper;

import com.example.workouttracker.exercise.ExerciseEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Exercise;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    ExerciseEntity toEntity(Exercise exerciseDto);

    Exercise toDto(ExerciseEntity exerciseEntity);

    List<Exercise> toDto(List<ExerciseEntity> exerciseEntity);

    List<ExerciseEntity> toEntity(List<Exercise> exerciseDto);
}
