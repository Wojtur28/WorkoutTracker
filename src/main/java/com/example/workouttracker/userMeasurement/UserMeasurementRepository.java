package com.example.workouttracker.userMeasurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserMeasurementRepository extends JpaRepository<UserMeasurementEntity, UUID> {

}
