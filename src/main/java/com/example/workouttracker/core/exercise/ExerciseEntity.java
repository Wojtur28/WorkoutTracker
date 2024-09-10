package com.example.workouttracker.core.exercise;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.exercise.set.ExerciseSetEntity;
import com.example.workouttracker.core.training.TrainingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "exercises")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseEntity extends AuditBase {

    private String name;

    private String description;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ExerciseSetEntity> sets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private TrainingEntity training;

    @Override
    public String toString() {
        return "ExerciseEntity{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
