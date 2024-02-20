package com.example.workouttracker.mapper;

import com.example.workouttracker.dto.UserDto;
import com.example.workouttracker.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = BaseMapper.class)
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDto(List<User> users);

    List<User> toEntity(List<UserDto> userDtos);

}
