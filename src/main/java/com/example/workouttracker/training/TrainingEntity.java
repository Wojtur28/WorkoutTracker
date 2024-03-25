package com.example.workouttracker.training;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.exercise.ExerciseEntity;
import com.example.workouttracker.trainingCategory.TrainingCategoryEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "trainings")
@Data
@NoArgsConstructor
public class TrainingEntity extends AuditBase {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "trainings", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExerciseEntity> exercises;

    @ElementCollection(targetClass = TrainingCategoryEntity.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "training_category", joinColumns = @JoinColumn(name = "training_id"))
    @Column(name = "category_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TrainingCategoryEntity> trainingCategories;
}
