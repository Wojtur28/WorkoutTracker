package com.example.workouttracker.user;

import com.example.workouttracker.AuditBase;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity(name = "user_measurements")
public class UserMeasurements extends AuditBase {

    private double weight;
    private double height;
    private double age;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;
}
