package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.ExerciseDto;
import com.example.workouttracker.exercise.ExerciseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    ExerciseEntity toEntity(ExerciseDto exerciseDto);

    ExerciseDto toDto(ExerciseEntity exerciseEntity);

    List<ExerciseDto> toDto(List<ExerciseEntity> exerciseEntity);

    List<ExerciseEntity> toEntity(List<ExerciseDto> exerciseDto);
}
