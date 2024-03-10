package com.example.workouttracker.training;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.exercise.Exercise;
import com.example.workouttracker.trainingCategory.TrainingCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "trainings")
@Data
@NoArgsConstructor
public class Training extends AuditBase {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exercise> exercises;

    @ElementCollection(targetClass = TrainingCategory.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "training_category", joinColumns = @JoinColumn(name = "training_id"))
    @Column(name = "category_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TrainingCategory> trainingCategories;
}
