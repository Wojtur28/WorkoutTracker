package com.example.workouttracker.core.training;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.trainingCategory.TrainingCategoryEntity;
import com.example.workouttracker.core.exercise.ExerciseEntity;
import com.example.workouttracker.core.user.UserEntity;
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

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExerciseEntity> exercises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ElementCollection(targetClass = TrainingCategoryEntity.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "training_category", joinColumns = @JoinColumn(name = "training_id"))
    @Column(name = "category_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TrainingCategoryEntity> trainingCategories;
}
