package com.example.workouttracker.mapper;

import com.example.workouttracker.training.TrainingEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Training;

import java.util.List;

@Mapper(componentModel = "spring", uses = ExerciseMapper.class)
public interface TrainingMapper {

    TrainingEntity toEntity(Training trainingDto);

    Training toDto(TrainingEntity trainingEntity);

    List<Training> toDto(List<TrainingEntity> trainingEntity);

}
