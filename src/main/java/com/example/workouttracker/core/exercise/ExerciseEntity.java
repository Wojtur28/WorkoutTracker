package com.example.workouttracker.core.exercise;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.training.TrainingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity(name = "exercises")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExerciseEntity extends AuditBase {

    private String name;

    private String description;

    @NotNull
    private int sets;

    @NotNull
    private int reps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private TrainingEntity training;
}
