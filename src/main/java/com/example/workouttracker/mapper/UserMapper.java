package com.example.workouttracker.mapper;

import com.example.workouttracker.user.UserEntity;
import org.mapstruct.Mapper;
import org.openapitools.model.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public interface UserMapper {

    User toDto(UserEntity userEntity);

    UserEntity toEntity(User userDto);

    List<User> toDto(List<UserEntity> userEntities);

    List<UserEntity> toEntity(List<User> userDtos);

}
