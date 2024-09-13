package com.example.workouttracker.core.userMeasurement;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity(name = "user_measurements")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMeasurementEntity extends AuditBase {

    private double weight;
    private double arms;
    private double chest;
    private double belly;
    private double legs;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
