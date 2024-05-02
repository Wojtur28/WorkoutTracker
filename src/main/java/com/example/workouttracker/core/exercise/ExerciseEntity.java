package com.example.workouttracker.core.exercise;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.training.TrainingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Entity(name = "exercises")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExerciseEntity extends AuditBase {

    private String name;

    private String description;

    @NotNull
    private int sets = 1;

    @NotNull
    private int reps = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private TrainingEntity training;
}
