package com.example.workouttracker.trainingCategory;

public enum TrainingCategory {

    POWERLIFTING("powerlifting"),
    OLYMPIC_WEIGHTLIFTING("olympic weightlifting"),
    CARDIO("cardio"),
    STRETCHING("stretching"),
    OTHER("other");
    private String name;

    private TrainingCategory(String name) {
        this.name = name;
    }
}
