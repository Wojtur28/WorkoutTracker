package com.example.workouttracker.user;

import com.example.workouttracker.AuditBase;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "user_measurements")
@Data
public class UserMeasurements extends AuditBase {

    private double weight;
    private double height;
    private double age;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;
}
