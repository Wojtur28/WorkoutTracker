package com.example.workouttracker.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class TrainingException extends RuntimeException {

    public enum FailReason {
        NOT_FOUND,
        USER_NOT_FOUND,
        EXERCISE_NOT_FOUND,
    }

    TrainingException.FailReason failReason;
}
