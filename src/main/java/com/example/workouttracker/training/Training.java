package com.example.workouttracker.training;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.exercise.Exercise;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "trainings")
@Data
public class Training extends AuditBase {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "training", fetch = FetchType.LAZY)

    private Set<Exercise> exercises;

    @ElementCollection(targetClass = TrainingCategory.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "training_category", joinColumns = @JoinColumn(name = "training_id"))
    @Column(name = "category_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TrainingCategory> trainingCategories;
}
