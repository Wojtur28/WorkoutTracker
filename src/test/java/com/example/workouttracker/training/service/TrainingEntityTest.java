package com.example.workouttracker.training.service;

import com.example.workouttracker.core.training.TrainingEntity;
import com.example.workouttracker.core.trainingCategory.TrainingCategoryEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrainingEntityTest {

    @Test
    public void newTrainingEntityShouldBeCreated() {
        TrainingEntity trainingEntity = new TrainingEntity("name",
                "description",
                null, null, List.of(TrainingCategoryEntity.POWERLIFTING));

        assertNotNull(trainingEntity);
        assertThat(trainingEntity.getName()).isEqualTo("name");
        assertThat(trainingEntity.getDescription()).isEqualTo("description");
        assertThat(trainingEntity.getTrainingCategories()).contains(TrainingCategoryEntity.POWERLIFTING);
    }

    @Test
    public void settersShouldWorkCorrectly() {
        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setName("name");
        trainingEntity.setDescription("description");
        trainingEntity.setTrainingCategories(List.of(TrainingCategoryEntity.POWERLIFTING));

        assertThat(trainingEntity.getName()).isEqualTo("name");
        assertThat(trainingEntity.getDescription()).isEqualTo("description");
        assertThat(trainingEntity.getTrainingCategories()).contains(TrainingCategoryEntity.POWERLIFTING);
    }
}
