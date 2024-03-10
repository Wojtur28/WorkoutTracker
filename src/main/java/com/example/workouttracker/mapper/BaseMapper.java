package com.example.workouttracker.mapper;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BaseMapper {

    default UUID map(java.lang.String value){
        if(value == null || value.isEmpty()){
            return null;
        }
        return UUID.fromString(value);
    }

    default String map(UUID uuid){
        if(uuid!=null) {
            return uuid.toString();
        }
        return null;
    }
}
