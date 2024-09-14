package com.example.workouttracker.core.exercise;

import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.exercise.set.ExerciseSetEntity;
import com.example.workouttracker.core.training.TrainingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "The name can't be blank")
    private String name;

    @NotNull(message = "The description can't be null")
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
