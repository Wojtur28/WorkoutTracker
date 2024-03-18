package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.UserDto;
import com.example.workouttracker.user.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public interface UserMapper {

    UserDto toDto(UserEntity userEntity);

    UserEntity toEntity(UserDto userDto);

    List<UserDto> toDto(List<UserEntity> userEntities);

    List<UserEntity> toEntity(List<UserDto> userDtos);

}
