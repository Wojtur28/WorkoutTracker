package com.example.workouttracker.mapper;

import com.example.workouttracker.core.exercise.ExerciseEntity;
import com.example.workouttracker.core.exercise.set.ExerciseSetEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Exercise;
import org.openapitools.model.ExerciseCreate;
import org.openapitools.model.SetExercise;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    Exercise toDto(ExerciseEntity exerciseEntity);

    List<Exercise> toDto(List<ExerciseEntity> exerciseEntity);

    ExerciseEntity toEntity(Exercise exercise);

    ExerciseEntity toEntity(ExerciseCreate exerciseCreate);

    SetExercise toDto(ExerciseSetEntity exerciseSetEntity);

    ExerciseSetEntity toEntity(SetExercise exerciseSet);

    List<ExerciseSetEntity> toEntity(List<SetExercise> exerciseSets);

}
