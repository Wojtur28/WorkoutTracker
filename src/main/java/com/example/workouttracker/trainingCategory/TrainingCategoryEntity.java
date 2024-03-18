package com.example.workouttracker.trainingCategory;

public enum TrainingCategoryEntity {

    POWERLIFTING("powerlifting"),
    OLYMPIC_WEIGHTLIFTING("olympic weightlifting"),
    CARDIO("cardio"),
    STRETCHING("stretching"),
    OTHER("other");
    private String name;

    private TrainingCategoryEntity(String name) {
        this.name = name;
    }
}
