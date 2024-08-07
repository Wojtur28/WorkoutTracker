package com.example.workouttracker.mapper;

import com.example.model.Exercise;
import com.example.model.ExerciseCreate;
import com.example.workouttracker.core.exercise.ExerciseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @Mapping(source = "sets", target = "sets")
    Exercise toDto(ExerciseEntity exerciseEntity);

    @Mapping(source = "sets", target = "sets")
    List<Exercise> toDto(List<ExerciseEntity> exerciseEntity);

    @Mapping(source = "sets", target = "sets")
    ExerciseEntity toEntity(Exercise exercise);

    @Mapping(source = "sets", target = "sets")
    ExerciseEntity toEntity(ExerciseCreate exerciseCreate);

}
