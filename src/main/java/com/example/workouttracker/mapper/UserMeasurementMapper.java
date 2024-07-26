package com.example.workouttracker.mapper;

import com.example.model.UserMeasurement;
import com.example.model.UserMeasurementCreate;
import com.example.workouttracker.core.userMeasurement.UserMeasurementEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMeasurementMapper {

    UserMeasurement toDto(UserMeasurementEntity userMeasurementEntity);

    List<UserMeasurement> toDto(List<UserMeasurementEntity> userMeasurementEntities);

    UserMeasurementEntity toEntity(UserMeasurement userMeasurement);

    UserMeasurementEntity toEntity(UserMeasurementCreate userMeasurementCreate);
}
