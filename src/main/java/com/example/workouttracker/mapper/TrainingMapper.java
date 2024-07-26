package com.example.workouttracker.mapper;

import com.example.model.Training;
import com.example.model.TrainingCreate;
import com.example.model.TrainingDetails;
import com.example.workouttracker.core.training.TrainingEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    Training toDto(TrainingEntity trainingEntity);

    List<Training> toDto(List<TrainingEntity> trainingEntity);

    TrainingEntity toEntity(Training training);

    TrainingDetails toDetailsDto(TrainingEntity trainingEntity);

    TrainingEntity toEntity(TrainingDetails trainingDetails);

    TrainingEntity toEntity(TrainingCreate trainingCreate);

}
