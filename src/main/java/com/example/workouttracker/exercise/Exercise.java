package com.example.workouttracker.exercise;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.training.Training;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity(name = "exercise")
@Data
@EqualsAndHashCode(callSuper = true)
public class Exercise extends AuditBase {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    @NotNull
    private Training training;

    private String name;

    private String description;


}
