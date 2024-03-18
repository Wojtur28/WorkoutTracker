package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.TrainingDto;
import com.example.workouttracker.training.TrainingEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ExerciseMapper.class)
public interface TrainingMapper {

    TrainingEntity toEntity(TrainingDto trainingDto);

    TrainingDto toDto(TrainingEntity trainingEntity);

    List<TrainingDto> toDto(List<TrainingEntity> trainingEntity);

}
