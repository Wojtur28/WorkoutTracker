package com.example.workouttracker.training;


import com.example.workouttracker.AuditBase;
import com.example.workouttracker.exercise.Exercise;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "training")
@Data
public class Training extends AuditBase {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "training", fetch = FetchType.LAZY)
    private Set<Exercise> exercises;
}
