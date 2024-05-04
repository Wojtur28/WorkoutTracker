package com.example.workouttracker.core.user;

public enum UserGender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");
    private String name;

    private UserGender(String name) {
        this.name = name;
    }
}
