package com.example.workouttracker.mapper;

import com.example.workouttracker.core.exercise.ExerciseEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Exercise;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    Exercise toDto(ExerciseEntity exerciseEntity);

    List<Exercise> toDto(List<ExerciseEntity> exerciseEntity);

    ExerciseEntity toEntity(Exercise exercise);

}
