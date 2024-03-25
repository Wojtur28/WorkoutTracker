package com.example.workouttracker.mapper;

import com.example.workouttracker.userMeasurement.UserMeasurementEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.UserMeasurement;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMeasurementMapper {

    UserMeasurement toDto(UserMeasurementEntity userMeasurementEntity);

    List<UserMeasurement> toDto(List<UserMeasurementEntity> userMeasurementEntities);
}
