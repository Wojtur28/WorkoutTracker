package com.example.workouttracker.userMeasurement;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "user_measurements")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMeasurement extends AuditBase {

    private double weight;
    private double height;
    private double age;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;
}
