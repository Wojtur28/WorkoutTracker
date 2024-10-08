package com.example.workouttracker.core.training;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.core.trainingCategory.TrainingCategoryEntity;
import com.example.workouttracker.core.exercise.ExerciseEntity;
import com.example.workouttracker.core.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "trainings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingEntity extends AuditBase {

    @Column(name = "name")
    @NotBlank(message = "The name can't be blank")
    private String name;

    @Column(name = "description")
    @NotNull(message = "The description can't be null")
    private String description;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ExerciseEntity> exercises = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ElementCollection(targetClass = TrainingCategoryEntity.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "training_category", joinColumns = @JoinColumn(name = "training_id"))
    @Column(name = "category_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TrainingCategoryEntity> trainingCategories;
}
