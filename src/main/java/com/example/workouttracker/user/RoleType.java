package com.example.workouttracker.user;

public enum RoleType {

    ADMINISTRATOR("administrator"),
    MODERATOR("moderator"),
    USER("user");
    private String name;

    RoleType(String name) {
        this.name = name;
    }
}
