package com.example.workouttracker.mapper;

import com.example.model.User;
import com.example.model.UserDetails;
import com.example.workouttracker.core.user.UserEntity;
import com.example.workouttracker.core.user.UserGender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "genders", source = "genders", qualifiedByName = "mapGendersToString")
    UserDetails toDetailsDto(UserEntity userEntity);

    @Mapping(target = "genders", source = "genders", qualifiedByName = "mapStringToGenders")
    UserEntity toEntity(UserDetails userDetails);

    @Mapping(target = "genders", source = "genders", qualifiedByName = "mapGendersToString")
    User toDto(UserEntity userEntity);

    @Mapping(target = "genders", source = "genders", qualifiedByName = "mapStringToGenders")
    UserEntity toEntity(User user);

    List<User> toDto(List<UserEntity> userEntities);

    @Named("mapGendersToString")
    default String mapGendersToString(Set<UserGender> genders) {
        return genders.stream().map(Enum::name).collect(Collectors.joining(","));
    }

    @Named("mapStringToGenders")
    default Set<UserGender> mapStringToGenders(String genders) {
        return Arrays.stream(genders.split(","))
                .map(UserGender::valueOf)
                .collect(Collectors.toSet());
    }
}