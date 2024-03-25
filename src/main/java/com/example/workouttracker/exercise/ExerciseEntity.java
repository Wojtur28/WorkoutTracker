package com.example.workouttracker.exercise;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.training.TrainingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "exercises")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExerciseEntity extends AuditBase {

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private TrainingEntity trainings;
}
