package com.example.workouttracker.mapper;

import com.example.workouttracker.core.training.TrainingEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.Training;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    Training toDto(TrainingEntity trainingEntity);

    List<Training> toDto(List<TrainingEntity> trainingEntity);

    TrainingEntity toEntity(Training training);



}
