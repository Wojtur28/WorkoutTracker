package com.example.workouttracker.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class TrainingException extends RuntimeException {

    public enum FailReason {
        NOT_FOUND,
        USER_NOT_FOUND,
        EXERCISE_NOT_FOUND,
    }

    TrainingException.FailReason failReason;

    public TrainingException(FailReason failReason) {
        super("Error occurred: " + failReason);
        this.failReason = failReason;
    }
}
