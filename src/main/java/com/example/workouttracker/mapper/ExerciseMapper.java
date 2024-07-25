package com.example.workouttracker.mapper;

import com.example.workouttracker.core.exercise.ExerciseEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Exercise;
import org.openapitools.model.ExerciseCreate;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    Exercise toDto(ExerciseEntity exerciseEntity);

    List<Exercise> toDto(List<ExerciseEntity> exerciseEntity);

    ExerciseEntity toEntity(Exercise exercise);

    ExerciseEntity toEntity(ExerciseCreate exerciseCreate);

}
