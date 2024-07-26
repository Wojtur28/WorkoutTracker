package com.example.workouttracker.mapper;

import com.example.model.User;
import com.example.model.UserDetails;
import com.example.workouttracker.core.user.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDto(UserEntity userEntity);

    UserEntity toEntity(User user);

    List<User> toDto(List<UserEntity> userEntities);

    UserDetails toDetailsDto(UserEntity userEntity);

    UserEntity toEntity(UserDetails userDetails);

}
