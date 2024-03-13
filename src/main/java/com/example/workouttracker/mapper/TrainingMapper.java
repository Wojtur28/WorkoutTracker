package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.TrainingDto;
import com.example.workouttracker.training.Training;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ExerciseMapper.class)
public interface TrainingMapper {

    Training toEntity(TrainingDto trainingDto);

    TrainingDto toDto(Training training);

    List<TrainingDto> toDto(List<Training> training);

}
