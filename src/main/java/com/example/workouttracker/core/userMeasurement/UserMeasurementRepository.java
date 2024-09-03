package com.example.workouttracker.core.userMeasurement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserMeasurementRepository extends JpaRepository<UserMeasurementEntity, UUID> {

    Page<UserMeasurementEntity> findByCreatedBy_Email(String email, Pageable pageable);
}
