package com.example.workouttracker.userMeasurement;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity(name = "user_measurements")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMeasurementEntity extends AuditBase {

    private double weight;
    private double height;
    private double age;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
