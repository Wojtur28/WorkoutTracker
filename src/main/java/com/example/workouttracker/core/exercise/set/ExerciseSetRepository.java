package com.example.workouttracker.core.exercise.set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface ExerciseSetRepository extends JpaRepository<ExerciseSetEntity, UUID> {


}
