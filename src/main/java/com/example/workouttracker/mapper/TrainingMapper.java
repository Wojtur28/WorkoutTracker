package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.ExerciseDto;
import com.example.workouttracker.dto.TrainingDto;
import com.example.workouttracker.exercise.Exercise;
import com.example.workouttracker.training.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ExerciseMapper.class)
public interface TrainingMapper {
    @Mapping(target = "exercises", source = "exercisesDto")
    Training toEntity(TrainingDto trainingDto);

    @Mapping(target = "exercisesDto", source = "exercises")
    TrainingDto toDto(Training training);
    @Mapping(target = "exercisesDto", source = "exercises")
    List<TrainingDto> toDto(List<Training> training);
    @Mapping(target = "exercises", source = "exercisesDto")
    List<Training> toEntity(List<TrainingDto> trainingDto);

}
