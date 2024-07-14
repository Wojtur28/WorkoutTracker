package com.example.workouttracker.core.exercise.set;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.exercise.ExerciseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "sets")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExerciseSetEntity extends AuditBase {

    @NotNull
    private int reps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private ExerciseEntity exercise;
}

