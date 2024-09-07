package com.example.workouttracker.core.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, UUID>{

    List<ExerciseEntity> findByNameAndCreatedBy_Email(String name, String createdBy_email);
}
