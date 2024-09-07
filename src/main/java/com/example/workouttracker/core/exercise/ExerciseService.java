package com.example.workouttracker.core.exercise;

import com.example.model.Exercise;
import com.example.workouttracker.mapper.ExerciseMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    public List<Exercise> getExercisesByName(String exerciseName) {
        log.info("Fetching exercises");
        return exerciseMapper.toDto(exerciseRepository.findByNameAndCreatedBy_Email(exerciseName, SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
