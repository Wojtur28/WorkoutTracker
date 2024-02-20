package com.example.workouttracker.exercise;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.training.Training;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity(name = "exercises")
@Data
@EqualsAndHashCode(callSuper = true)
public class Exercise extends AuditBase {

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "training_id")
    private Training training;
}
