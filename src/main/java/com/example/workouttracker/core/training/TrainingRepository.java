package com.example.workouttracker.core.training;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingRepository extends JpaRepository<TrainingEntity, UUID> {

    Page<TrainingEntity> findByCreatedBy_Email(String email, Pageable pageable);

    List<TrainingEntity> findByCreatedBy_Email(String email);
}
